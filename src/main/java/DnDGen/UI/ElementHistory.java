package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.DB.HistoryModel;
import DnDGen.TSWP1.NpcClass;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.util.*;

public class ElementHistory {
    private final VBox historyPage;
    private final Scene scene;
    private Scene generatePage;
    private final Stage window;
    private final DBMS dbms;
    private final GridPane npcGrid;

    ElementHistory(Stage window, DBMS dbms) {
        this.window = window;
        this.dbms = dbms;

        historyPage = new VBox();
        npcGrid = new GridPane();
        historyPage.getChildren().add(npcGrid);

        scene = new Scene(historyPage);
    }

    /**
     * Erzeugt und steuert die menuBar
     * @param generatePage Scene von der Seite für die NPC generation, für die Rückführung auf diese Seite
     * @author Marie
     */
    public void menuBar(Scene generatePage) {
        Menu menu = new Menu("Navigation");
        MenuBar menuBar = new MenuBar();
        this.generatePage = generatePage;

        // Items der MenuBar hinzufügen
        MenuItem menuItemHistory = new MenuItem("History");
        MenuItem menuItemGenerator = new MenuItem("Generator");

        menu.getItems().addAll(menuItemHistory,menuItemGenerator);
        menuBar.getMenus().add(menu);

        // Funktion für die MenuBar Items
        menuItemGenerator.setOnAction(e -> {
            e.consume();
            npcGrid.getChildren().removeAll();
            window.setScene(generatePage);
        });

        // Die MenuBar dem Seitenlayout hinzufügen
        this.historyPage.getChildren().addFirst(menuBar);
    }

    /**
     * GridPane für die NPC Informationen eines NPC für die Darstellung als Kachel
     * @param npcE der NPC der in die Kachel gepackt wird
     * @return gibt die Daten als VBox (in Kachel form) zurück
     * @author Marie
     */
    private VBox makeGridNPC(NpcClass npcE, Integer id){
        VBox npcVBox = new VBox();
        GridPane npcGrid = new GridPane();
        npcGrid.setPadding(new Insets(10, 10, 10, 10));
        npcGrid.setVgap(7);
        npcGrid.setHgap(10);

        // Name
        Label name = new Label("Name: " + npcE.getName());
        GridPane.setConstraints(name,0,0);

        // Level
        Label level = new Label("Level: " + npcE.getLevel());
        GridPane.setConstraints(level,0,3);

        // Rasse
        Label race = new Label("Race: " + npcE.getRace());
        GridPane.setConstraints(race,0,1);

        // Klasse
        Label clas = new Label("Class: " + npcE.getGenClass());
        GridPane.setConstraints(clas,0,4);

        // Job
        Label job = new Label("Job: " + npcE.getJob());
        GridPane.setConstraints(job,0,2 );

        npcGrid.getChildren().addAll(name,level,race,clas,job);


        // Ability-scores
        GridPane abilityGrid = new GridPane();
        abilityGrid.setPadding(new Insets(0, 10, 10, 10));
        abilityGrid.setVgap(7);
        abilityGrid.setHgap(10);


        ArrayList<String> abilityString = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));
        ArrayList<Integer> ability = (ArrayList<Integer>) npcE.getAbilityScores();
        ArrayList<Integer> abilityMod = (ArrayList<Integer>) npcE.getAbilityModifier();

        // geht jede Ability durch
        for(int i = 0; i < abilityString.size(); i++) {
            Label label = new Label(abilityString.get(i));
            GridPane.setConstraints(label,i,0);
            String string;

            if(ability.size() > i) {
                if(abilityMod.get(i) < 0) {
                    string = (ability.get(i) + "" + abilityMod.get(i));
                } else {string = ability.get(i) + "+" + abilityMod.get(i);}

                Label abilityL = new Label(string);
                GridPane.setConstraints(abilityL, i, 1);

                abilityGrid.getChildren().add(abilityL);
            }

            abilityGrid.getChildren().addAll(label);
        }

        npcVBox.getChildren().addAll(npcGrid,abilityGrid);
        npcVBox.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");

        // Event-Handler für Klick-Ereignis. Wenn die Box gedrückt wird, passiert das:

        npcVBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleNpcClick(event, id));


        /*{
            event.consume();
            ElementEdit elementEdit = new ElementEdit(window, npc, dbms);
            System.out.println(npc.getName());
            elementEdit.menuBar(generatePage, scene);
            window.setTitle("Edit");
            window.setScene(elementEdit.getScene());
        });


         */

        // Event-Handler für Hover-Ereignis
        npcVBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> npcVBox.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;"));

        npcGrid.addEventHandler(MouseEvent.MOUSE_EXITED, event -> npcVBox.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;"));

        return npcVBox;
    }

    /**
     * MouseEvent für die VBox der History Seite
     * @param event was für ein MouseEvent, → Click
     * @param id die Id des NPC
     * @author Marie
     */
    private void handleNpcClick(MouseEvent event, Integer id) {
        event.consume();

        ElementEdit elementEdit = new ElementEdit(window, id,dbms);

        elementEdit.menuBar(generatePage, scene);
/*
        ElementDetail elementDetail = new ElementDetail(window, npc, id, dbms);
        elementDetail.menuBar(generatePage, scene);
*/
        window.setTitle("Edit");
        window.setScene(elementEdit.getScene());
        // window.setScene(elementDetail.getScene());
    }

    /**
     * Ausgabe von der Scene und erstellung des Layouts
     * @return gibt die Scene zurück
     * @author Marie
     */
    public Scene getScene(){
        int pos = 0, row = 0;

        // fügt für jeden NPC eine VBox hinzu und ordnet sie an
        NpcClass npc = new NpcClass();
        for(HistoryModel npcList :  this.dbms.getHistory()) {
            npc.historyEntryToNpc(npcList);
            VBox grid = makeGridNPC(npc,npcList.getHistoryId() );
            GridPane.setConstraints(grid, pos,row);
            pos++;
            if(pos > 4) {
                pos = 0;
                row++;
            }

            npcGrid.getChildren().add(grid);
        }

        return scene;
    }
}

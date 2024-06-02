package DnDGen.UI;

import DnDGen.DB.DBMS;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ElementHistory {
    private VBox historyPage;
    private Scene scene, generatePage;
    private MenuBar menuBar;
    private Stage window;
    private DBMS dbms;
    private GridPane npcGrid;

    ElementHistory(Stage window, DBMS dbms) {
        this.window = window;
        this.dbms = dbms;

        historyPage = new VBox();
        npcGrid = new GridPane();
        historyPage.getChildren().add(npcGrid);

        scene = new Scene(historyPage);
    }

    // Erzeugt und steuert die menuBar
    public void menuBar(Scene generatePage) {
        Menu menu = new Menu("Navigation");
        this.menuBar = new MenuBar();
        this.generatePage = generatePage;

        MenuItem menuItemHistory = new MenuItem("History");
        MenuItem menuItemGenerator = new MenuItem("Generator");

        /*
        menuItemHistory.setOnAction(e -> {
            e.consume();
            window.setScene(elementHistory.getScene());
            menuItemGenerator.setVisible(true);
            menuItemHistory.setVisible(false);
        });
        */

        menu.getItems().addAll(menuItemHistory,menuItemGenerator);
        menuBar.getMenus().add(menu);


        // Zusatz für die MenuBar
        menuItemGenerator.setOnAction(e -> {
            e.consume();
            npcGrid.getChildren().removeAll();
            window.setScene(generatePage);
        });

        this.historyPage.getChildren().addFirst(menuBar);

    }

    // Grid für die NPC Informationen
    private VBox makeGridNPC(ArrayList<Object> npc){
        VBox npcVBox = new VBox();
        GridPane npcGrid = new GridPane();
        npcGrid.setPadding(new Insets(10, 10, 10, 10));
        npcGrid.setVgap(7);
        npcGrid.setHgap(10);

        // Name
        Label name = new Label("Name: " + npc.get(2));
        GridPane.setConstraints(name,0,0);

        // Level
        Label level = new Label("Level: " + String.valueOf(npc.get(3)));
        GridPane.setConstraints(level,0,3);

        // Rasse
        Label race = new Label("Rasse: " + npc.get(5));
        GridPane.setConstraints(race,0,1);

        // Klasse
        Label clas = new Label("Klasse: " + npc.get(4));
        GridPane.setConstraints(clas,0,4);

        // Job
        Label job = new Label("Beruf: " + npc.get(6));
        GridPane.setConstraints(job,0,2 );

        npcGrid.getChildren().addAll(name,level,race,clas,job);


        // Ability-scores
        GridPane abilityGrid = new GridPane();
        abilityGrid.setPadding(new Insets(0, 10, 10, 10));
        abilityGrid.setVgap(7);
        abilityGrid.setHgap(10);

        ArrayList<String> abilityString = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));
        //System.out.println(npc);
        ArrayList<Integer> ability = (ArrayList<Integer>) npc.get(11);
        for(int i = 0; i < abilityString.size(); i++) {
            Label label = new Label(abilityString.get(i));
            GridPane.setConstraints(label,i,0);

            if(ability.size() > i) {
                Label abilityL = new Label(ability.get(i).toString());
                GridPane.setConstraints(abilityL, i, 1);
                abilityGrid.getChildren().add(abilityL);
            }

            abilityGrid.getChildren().addAll(label);
        }

        npcVBox.getChildren().addAll(npcGrid,abilityGrid);
        npcVBox.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");

        // Event-Handler für Klick-Ereignis
        npcVBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            event.consume();
            //System.out.println("GridPane clicked!");
            // Hier können Sie zusätzliche Logik für den Klick einfügen
            ElementEdit elementEdit = new ElementEdit(window,npc,dbms);
            elementEdit.menuBar(generatePage,scene);
            window.setTitle("Edit");
            window.setScene(elementEdit.getScene());
        });

        // Event-Handler für Hover-Ereignis
        npcVBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            npcVBox.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");
        });

        npcGrid.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            npcVBox.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
        });

        return npcVBox;
    }

    // Ausgabe von der Scene
    public Scene getScene(){
        int pos = 0, row = 0;

        // fügt für jeden NPC eine VBox hinzu und ordnet sie an
        for(Object npcc :  sortList(this.dbms.getHistory())) {
            VBox grid = makeGridNPC((ArrayList<Object>) npcc);
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

    // sortieren der NPC's nach Datum
    public List<Object> sortList(List<Object> npcc) {

        // Define a date formatter to parse the datetime strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Sort the list based on the last element which is a datetime string
        npcc.sort((o1, o2) -> {
            ArrayList<Object> list1 = (ArrayList<Object>) o1;
            ArrayList<Object> list2 = (ArrayList<Object>) o2;

            LocalDateTime dateTime1 = (LocalDateTime) list1.get(list1.size() - 1);
            LocalDateTime dateTime2 = (LocalDateTime) list2.get(list2.size() - 1);

            return dateTime2.compareTo(dateTime1);
        });

        return npcc;


    }

}

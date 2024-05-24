package DnDGen.UI;

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

import java.util.ArrayList;
import java.util.Arrays;

public class ElementHistory {
    private VBox historyPage;
    private Scene scene, generatePage;
    private ArrayList<String> npcHistoryList;
    private MenuBar menuBar;
    private Stage window;

    ElementHistory(Stage window) {
        this.window = window;

        historyPage = new VBox();

        ArrayList<String> np = new ArrayList<>(Arrays.asList("Test1", "Test2", "Test3"));

        // npc info als item anzeigen
        historyPage.getChildren().add(makeGridNPC(np));


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
            window.setScene(generatePage);
        });

        this.historyPage.getChildren().addFirst(menuBar);

    }





    // Grid für die NPC Informationen
    private GridPane makeGridNPC(ArrayList<String> npc){
        GridPane npcGrid = new GridPane();
        npcGrid.setPadding(new Insets(10, 10, 10, 10));
        npcGrid.setVgap(7);
        npcGrid.setHgap(10);
        npcGrid.setMinWidth(300);

        /*  Name Level      STR ...
            Rasse Klasse
            Job
         */

        // Name
        Label name = new Label("Name: " + npc.get(0));
        GridPane.setConstraints(name,0,0);

        // Level
        Label level = new Label("Level: " + npc.get(0));
        GridPane.setConstraints(level,1,0);

        // Rasse
        Label race = new Label("Rasse: " + npc.get(0));
        GridPane.setConstraints(race,0,1);

        // Klasse
        Label clas = new Label("Klasse: " + npc.get(0));
        GridPane.setConstraints(clas,1,1);

        // Job
        Label job = new Label("Beruf: " + npc.get(0));
        GridPane.setConstraints(job,0,2 );

        npcGrid.getChildren().addAll(name,level,race,clas,job);

        // Abilityscores
        ArrayList<String> abilityString = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));
        for(int i = 2; i < 5; i++) {
            for(int j = 0; j < abilityString.size(); j++) {
                Label label = new Label(abilityString.get(j));
                GridPane.setConstraints(label,i,j );
                npcGrid.getChildren().add(label);
            }

            // Placeholder

        }

        npcGrid.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");

        // Event-Handler für Klick-Ereignis
        npcGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            event.consume();
            System.out.println("GridPane clicked!");
            // Hier können Sie zusätzliche Logik für den Klick einfügen
            ElementEdit elementEdit = new ElementEdit(window,npc);
            elementEdit.menuBar(generatePage,scene);
            window.setTitle("Edit");
            window.setScene(elementEdit.getScene());
        });

        // Event-Handler für Hover-Ereignis
        npcGrid.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            npcGrid.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");
        });

        npcGrid.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            npcGrid.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
        });

        return npcGrid;
    }



    // Ausgabe von der Scene
    public Scene getScene(){
        return scene;
    }

}

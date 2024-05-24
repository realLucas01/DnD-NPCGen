package DnDGen.UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementEdit {
    Stage window;
    Scene scene;
    ArrayList<String> npc;
    VBox npcDate;
    MenuBar menuBar;

    ElementEdit(Stage window, ArrayList<String> npc) {
        this.window = window;
        this.npc = npc;
        npcDate = new VBox();

        makeEditLayout();

        scene = new Scene(npcDate);

    }

    // Erzeugt und steuert die menuBar
    public void menuBar(Scene generatePage, Scene elementHistory) {
        Menu menu = new Menu("Navigation");
        this.menuBar = new MenuBar();

        MenuItem menuItemHistory = new MenuItem("History");
        MenuItem menuItemGenerator = new MenuItem("Generator");


        menuItemHistory.setOnAction(e -> {
            e.consume();
            window.setScene(elementHistory);
            menuItemGenerator.setVisible(true);
            menuItemHistory.setVisible(false);
        });


        menu.getItems().addAll(menuItemHistory,menuItemGenerator);
        menuBar.getMenus().add(menu);


        // Zusatz für die MenuBar
        menuItemGenerator.setOnAction(e -> {
            e.consume();
            window.setScene(generatePage);
        });

        this.npcDate.getChildren().addFirst(menuBar);

    }


    public void makeEditLayout() {
        List<String> listNpc = new ArrayList<>();
        String stringNpc = "";

        //Name, Klasse, Rasse, Alignment
        GridPane nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(4);
        nameClassGrid.setHgap(25);

        // Name
        Label nameL      = new Label("Name: ");
        GridPane.setConstraints(nameL,0,0);
        TextField nameF = new TextField(npc.get(2));
        GridPane.setConstraints(nameF,1,0);

        // Level
        Label levelL     = new Label("Level: ");
        GridPane.setConstraints(levelL,2,0);
        TextField levelF = new TextField(String.valueOf(npc.get(3)));
        GridPane.setConstraints(levelF,3,0);

        // Klasse
        Label classL     = new Label("Klasse: ");
        GridPane.setConstraints(classL,0,1);
        TextField classF = new TextField(npc.get(4));
        GridPane.setConstraints(classF,1,1);

        // Rasse
        Label raceL      = new Label("Rasse: ");
        GridPane.setConstraints(raceL,2,1);
        TextField raceF = new TextField(npc.get(5));
        GridPane.setConstraints(raceF,3,1);

        // Job
        Label jobL       = new Label("Job: ");
        GridPane.setConstraints(jobL,0,2);
        TextField jobF = new TextField(npc.get(6));
        GridPane.setConstraints(jobF,1,2);

        // Deity
        Label deityL     = new Label("Gottheit: ");
        GridPane.setConstraints(deityL,0,3);
        TextField deityF = new TextField(npc.get(5));
        GridPane.setConstraints(deityF,1,3);

        // Alignment
        Label alignmentL = new Label("Alignment: ");
        GridPane.setConstraints(alignmentL,2,3);
        TextField alignmentF = new TextField(npc.get(5));
        GridPane.setConstraints(alignmentF,3,3);

        // Sex
        Label sexL       = new Label("Geschlecht: ");
        GridPane.setConstraints(sexL,0,4);
        TextField sexF = new TextField(npc.get(5));
        GridPane.setConstraints(sexF,1,4);

        // Age
        Label ageL       = new Label("Alter: ");
        GridPane.setConstraints(ageL,2,4);
        TextField ageF = new TextField(String.valueOf(npc.get(7)));
        GridPane.setConstraints(ageF,3,4);

        // AmorClass
        Label armorClassL = new Label("Rüstungsklasse: ");
        GridPane.setConstraints(armorClassL,0,5);
        TextField amorClassF = new TextField(String.valueOf(npc.get(7)));
        GridPane.setConstraints(amorClassF,1,5);

        // HitPoints
        Label hitPointsL  = new Label("Trefferpunkte: ");
        GridPane.setConstraints(hitPointsL,2,5);
        TextField hitPointsF = new TextField(String.valueOf(npc.get(8)));
        GridPane.setConstraints(hitPointsF,3,5);

        nameClassGrid.getChildren().addAll(nameL, classL, jobL, raceL, alignmentL, ageL, sexL, deityL, levelL,
                                           armorClassL, hitPointsL, nameF, classF, jobF, raceF, alignmentF, ageF, sexF,
                                           deityF, levelF, amorClassF, hitPointsF);


        //Armor class, Hit points, Speed, Language, Feature
        GridPane acHpSpLangGrid = new GridPane();
        acHpSpLangGrid.setPadding(new Insets(5, 5, 5, 5));
        acHpSpLangGrid.setVgap(4);
        acHpSpLangGrid.setHgap(5);
        //acHpSpLangGrid.getChildren().remove(4,acHpSpLangGrid.getChildren().size());


        Label speedL      = new Label("Geschwindigkeit: ");
        GridPane.setConstraints(speedL,0,2);
        Label languagesL   = new Label("Sprachen: ");
        GridPane.setConstraints(languagesL,0,4);

        //AmorClass, HitPoints
        Label aCL = new Label(String.valueOf(npc.get(1)));
        GridPane.setConstraints(aCL,1,0);
        Label hPL = new Label(String.valueOf(npc.get(1)));
        GridPane.setConstraints(hPL,1,1);
        acHpSpLangGrid.getChildren().addAll(speedL,aCL,hPL);




        npcDate.getChildren().addAll(nameClassGrid,acHpSpLangGrid);



    }




    // gibt die Scene zurück
    public Scene getScene() { return scene;}

}

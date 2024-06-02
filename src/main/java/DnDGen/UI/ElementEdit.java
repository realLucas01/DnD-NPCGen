package DnDGen.UI;

import DnDGen.DB.DBMS;
import javafx.collections.FXCollections;
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
import java.util.Collections;
import java.util.List;

public class ElementEdit {
    private Stage window;
    private Scene scene;
    private ArrayList<Object> npc;
    private VBox npcDate;
    private MenuBar menuBar;
    private DBMS dbms;

    ElementEdit(Stage window, ArrayList<Object> npc, DBMS dbms) {
        this.window = window;
        this.npc = npc;
        npcDate = new VBox();
        this.dbms = dbms;

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
        TextField nameF = new TextField((String) npc.get(2));
        GridPane.setConstraints(nameF,1,0);

        // Level
        Label levelL     = new Label("Level: ");
        GridPane.setConstraints(levelL,2,0);
        final ComboBox cbLevel = new ComboBox();
        GridPane.setConstraints(cbLevel, 1, 4,2,1);
        for(int i = 1; i <= 20; i++) {cbLevel.getItems().add(i);}
        cbLevel.getSelectionModel().select(cbLevel.getItems().indexOf(npc.get(3)));
        GridPane.setConstraints(cbLevel,3,0);

        // Klasse
        Label classL     = new Label("Klasse: ");
        GridPane.setConstraints(classL,0,1);
        final ComboBox cbClass = new ComboBox<>();
        ArrayList<String> list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        System.out.println(npc.get(list.indexOf(npc.get(4))));
        cbClass.getSelectionModel().select(list.indexOf(npc.get(4)));
        GridPane.setConstraints(cbClass,1,1);

        // Rasse
        Label raceL      = new Label("Rasse: ");
        GridPane.setConstraints(raceL,2,1);
        final ComboBox cbRace = new ComboBox<>();
        list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        cbRace.getSelectionModel().select(list.indexOf(npc.get(5)));
        GridPane.setConstraints(cbRace,3,1);

        // Job
        Label jobL       = new Label("Job: ");
        GridPane.setConstraints(jobL,0,2);
        final ComboBox cbJob = new ComboBox<>();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        cbJob.getSelectionModel().select(list.indexOf(npc.get(6)));
        GridPane.setConstraints(cbJob,1,2);

        // Deity
        Label deityL     = new Label("Gottheit: ");
        GridPane.setConstraints(deityL,0,3);
        TextField deityF = new TextField((String) npc.get(27));
        GridPane.setConstraints(deityF,1,3);

        // Alignment
        Label alignmentL = new Label("Alignment: ");
        GridPane.setConstraints(alignmentL,2,3);
        TextField alignmentF = new TextField((String) npc.get(26));
        GridPane.setConstraints(alignmentF,3,3);

        // Sex
        Label sexL       = new Label("Geschlecht: ");
        GridPane.setConstraints(sexL,0,4);
        final ComboBox cbGender = new ComboBox<>(FXCollections.observableArrayList("female","male"));
        cbGender.getSelectionModel().select(cbGender.getItems().indexOf(npc.get(24)));
        GridPane.setConstraints(cbGender,1,4);

        // Age
        Label ageL       = new Label("Alter: ");
        GridPane.setConstraints(ageL,2,4);
        TextField ageF = new TextField(String.valueOf(npc.get(25)));
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
                                           armorClassL, hitPointsL, nameF, cbClass, cbJob, cbRace, alignmentF, ageF,
                                           cbGender, deityF, cbLevel, amorClassF, hitPointsF);


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

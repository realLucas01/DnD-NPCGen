package DnDGen.UI;

//import java.awt.Button;
//import java.awt.Insets;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class UI extends Application {
    Stage window;
    Scene generatePage, npcPage, historyPage, editPage;
    String name, klasse, rasse;


    public static void main(String[] args) {
        launch(args);
    }


    //Stage -> Ist ein Fenster
    //Scene -> Ist der Inhalt des Fensters
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("DnD NPC Generator");

        GridPane layoutGenerateGrid = new GridPane();
        layoutGenerateGrid.setPadding(new Insets(10, 10, 10, 10));
        layoutGenerateGrid.setVgap(7);
        layoutGenerateGrid.setHgap(10);


        //Menü
        Menu menu = new Menu("Menu 1");
        MenuBar menuBar = new MenuBar();

        menu.setOnShowing(e -> { System.out.println("Showing Menu 1"); });
        menu.setOnShown  (e -> { System.out.println("Shown Menu 1"); });
        menu.setOnHiding (e -> { System.out.println("Hiding Menu 1"); });
        menu.setOnHidden (e -> { System.out.println("Hidden Menu 1"); });

        MenuItem menuItem1 = new MenuItem("Item 1");
        MenuItem menuItem2 = new MenuItem("Item 2");

        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);

        menuBar.getMenus().add(menu);


        //Seite generatePage
        //Rasse
        Label labelRace = new Label("Rasse:");
        GridPane.setConstraints(labelRace, 1, 1);

        final ComboBox cbRace = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbRace, 2, 1);
        cbRace.getItems().addAll("Option 1", "Option 2", "Option 2");
        cbRace.getSelectionModel().selectFirst();


        //Klasse
        Label labelClass = new Label("Klasse:");
        GridPane.setConstraints(labelClass, 1, 2);

        final ComboBox cbClass = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbClass, 2, 2);
        cbClass.getItems().addAll("Option 1", "Option 2", "Option 2");
        cbClass.getSelectionModel().selectFirst();


        //Job
        Label labelJob = new Label("Beruf:");
        GridPane.setConstraints(labelJob, 1, 3);

        final ComboBox cbJob = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbJob, 2, 3);
        cbJob.getItems().addAll("Option 1", "Option 2", "Option 2");
        cbJob.getSelectionModel().selectFirst();


        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 1, 4);

        final ComboBox cbLevel = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 2, 4);
        cbLevel.getItems().addAll("Option 1", "Option 2", "Option 2");
        cbLevel.getSelectionModel().selectFirst();



        //Als Textfeld





        //Als Tabelle/einzelne Bausteine
        VBox npcStack = new VBox();
        //GridPane.setConstraints(npcStack,4,0,4,25);

        //Name, Klasse, Rasse, Alignment
        GridPane nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(2);
        nameClassGrid.setHgap(5);

        Label nameL      = new Label("Name: ");
        Label classL     = new Label("Klasse: ");
        Label raceL      = new Label("Rasse: ");
        Label alignmentL = new Label("Alignment: ");

        GridPane.setConstraints(nameL,0,0,2,1);
        GridPane.setConstraints(classL,0,1,2,1);
        GridPane.setConstraints(raceL,0,2);
        GridPane.setConstraints(alignmentL,1,2);

        nameClassGrid.getChildren().addAll(nameL, classL, raceL, alignmentL);

        //Armor class, Hit points, Speed
        VBox armorHitSpeedPane = new VBox();
        Label armorClassL = new Label("Rüstungsklasse: ");
        Label hitPointsL = new Label("Trefferpunkte: ");
        Label speedL = new Label("Geschwindigkeit: ");

        armorHitSpeedPane.getChildren().addAll(armorClassL,hitPointsL,speedL);
        armorHitSpeedPane.setPadding(new Insets(10, 10, 10, 10));

        //Stats
        HBox statsBox = new HBox();
        statsBox.setPadding(new Insets(10,10,10,10));
        statsBox.setSpacing(5);

        Label strL = new Label("Str\n");
        Label dexL = new Label("Dex\n");
        Label conL = new Label("Con\n");
        Label intL = new Label("Int\n");
        Label wisL = new Label("Wis\n");
        Label chaL = new Label("Cha\n");

        statsBox.getChildren().addAll(strL, dexL,conL,intL,wisL,chaL);

        //Skill, Senses
        VBox skillSensesBox = new VBox();
        TextFlow skillTF = new TextFlow();
        Text text1=new Text("Skill1: ");
        text1.setStyle("-fx-font-weight: bold");
        Text text2=new Text("Some Text");
        text2.setStyle("-fx-font-weight: normal");
        skillTF.getChildren().addAll(text1, text2);

        TextFlow senseTF = new TextFlow();
        Text text3=new Text("Sense1: ");
        text3.setStyle("-fx-font-weight: bold");
        Text text4=new Text("Some Text");
        text4.setStyle("-fx-font-weight: normal");
        senseTF.getChildren().addAll(text3, text4);

        skillSensesBox.getChildren().addAll(skillTF,senseTF);
        skillSensesBox.setPadding(new Insets(10, 10, 10, 10));

        //Spell, Action
        VBox spellActionBox = new VBox();
        TextFlow spellTF = new TextFlow();
        text1 = new Text("Zauber: ");
        text1.setStyle("-fx-font-weight: bold");
        text2 = new Text("Some Text");
        text2.setStyle("-fx-font-weight: normal");
        spellTF.getChildren().addAll(text1, text2);

        TextFlow actionTF = new TextFlow();
        text3 = new Text("Aktionen: ");
        text3.setStyle("-fx-font-weight: bold");
        text4 = new Text("Some Text");
        text4.setStyle("-fx-font-weight: normal");
        actionTF.getChildren().addAll(text3, text4);

        spellActionBox.getChildren().addAll(spellTF,actionTF);
        spellActionBox.setPadding(new Insets(10, 10, 10, 10));


        Line line1 = new Line(0.0f,0.0f,200.0f,0.0f);
        Line line2 = new Line(0.0f,0.0f,200.0f,0.0f);
        Line line3 = new Line(0.0f,0.0f,200.0f,0.0f);
        Line line4 = new Line(0.0f,0.0f,200.0f,0.0f);

        npcStack.getChildren().addAll(nameClassGrid,line1,armorHitSpeedPane,line2,statsBox,line3,skillSensesBox,line4,spellActionBox);
        npcStack.setVisible(false);
        //layoutGenerateGrid.getChildren().add(npcStack);


        //Button
        Button buttonGenerate = new Button("Generieren");
        GridPane.setConstraints(buttonGenerate, 1, 6);
        buttonGenerate.setOnAction(e -> {
           //Parameter für generieren  (cbRace.getValue(),cbClass.getValue(),cbJob.getValue(),cbLevel.getValue())

        npcStack.setVisible(true);




        });













        //Layout der Seite

        layoutGenerateGrid.getChildren().addAll(labelRace, labelClass, labelJob, labelLevel, cbRace, cbClass,
                                                cbJob, cbLevel, buttonGenerate
        );
        VBox layoutGenerateVBox = new VBox(20);
        HBox gridNpc = new HBox();
        gridNpc.getChildren().addAll(layoutGenerateGrid, npcStack);
        layoutGenerateVBox.getChildren().addAll(menuBar,gridNpc);

        generatePage = new Scene(layoutGenerateVBox, 800, 600);















    // ---Stuff zum nachsehen -> wird noch reduziert und gelöscht---
    /*
        //Seite 1 - Labels
        Label label1 = new Label("Das ist die erste Seite");
        GridPane.setConstraints(label1, 0, 0);

        Label label1Name = new Label("Name:");
        GridPane.setConstraints(label1Name, 0, 1);
        Label label1Klasse = new Label("Klasse:");
        GridPane.setConstraints(label1Klasse, 0, 2);
        Label label1Rasse = new Label("Ethnie:");
        GridPane.setConstraints(label1Rasse, 0, 3);


        //Seite 1 - Text Input
        TextField nameInput = new TextField("Max Muster");
        GridPane.setConstraints(nameInput, 1, 1);

        TextField klasseInput = new TextField();
        klasseInput.setPromptText("Paladin");
        GridPane.setConstraints(klasseInput, 1, 2);

        TextField rasseInput = new TextField();
        GridPane.setConstraints(rasseInput, 1, 3);


        //Seite 2 - Labels
        Label label2Name = new Label();
        Label label2Klasse = new Label();
        Label label2Rasse = new Label();


        //Man kann den Fenstertitel auf unterschiedliche weise festlegen
        Button button1 = new Button("Button Title");
        button1.setText("Zu Seite 2");
        GridPane.setConstraints(button1, 1, 4);

        //Anonyme inner Class
        button1.setOnAction(e -> {
            label2Name.setText("Name: " + nameInput.getText());
            label2Klasse.setText("Klasse: " + klasseInput.getText());
            label2Rasse.setText("Ethnie: " + rasseInput.getText());


            name = nameInput.getText();
            klasse = klasseInput.getText();
            rasse = rasseInput.getText();
            window.setScene(page2);
        });

        //Kurzform einer Anonymen inner Class
        Button button2 = new Button("Zu Seite 1");
        button2.setOnAction(e -> window.setScene(page1));


        //ComboBox
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        final ComboBox comboBox = new ComboBox(options);

        comboBox.getItems().addAll(
                "Option 4",
                "Option 5",
                "Option 6"
        );


        //Layout 1 - Kinder sind in Vertikalen Spalten
        VBox layout1 = new VBox(20);
        //layout1.getChildren().addAll(label1,button1);
        //page1 = new Scene(layout1,200,200);
        layout1.getChildren().addAll(label2Name, label2Klasse, label2Rasse, button2, comboBox);
        page2 = new Scene(layout1, 300, 400);


        //Layout 2 - Kinder sind auf Stapel/Stack
        StackPane layout2 = new StackPane();
        //layout2.getChildren().add(button2);
        //page2 = new Scene(layout2,600,300);


        //Layout 3 - Kinder sind in Tabelle/Grid
        GridPane layout3 = new GridPane();
        layout3.setPadding(new Insets(10, 10, 10, 10));
        layout3.setVgap(7);
        layout3.setHgap(10);
        layout3.getChildren().addAll(label1, label1Klasse, label1Name, label1Rasse, nameInput, klasseInput, rasseInput, button1);
        page1 = new Scene(layout3, 300, 400);
    */

        //Es startet auf der generieren Seite
        //window.setScene(npcPage);
        window.setScene(generatePage);
        window.show();
    }
}
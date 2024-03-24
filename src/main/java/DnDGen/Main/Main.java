package DnDGen.Main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    Stage window;
    Scene generatePage, historyPage, editPage;
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


        //Seite generatePage
        //Rasse
        Label labelRace = new Label("Rasse:");
        GridPane.setConstraints(labelRace, 1, 1);

        final ComboBox cbRace = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbRace, 2, 1);
        cbRace.getItems().addAll("Option 1", "Option 2", "Option 2");


        //Klasse
        Label labelClass = new Label("Klasse:");
        GridPane.setConstraints(labelClass, 1, 2);

        final ComboBox cbClass = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbClass, 2, 2);
        cbClass.getItems().addAll("Option 1", "Option 2", "Option 2");


        //Job
        Label labelJob = new Label("Beruf:");
        GridPane.setConstraints(labelJob, 1, 3);

        final ComboBox cbJob = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbJob, 2, 3);
        cbJob.getItems().addAll("Option 1", "Option 2", "Option 2");


        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 1, 4);

        final ComboBox cbLevel = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 2, 4);
        cbLevel.getItems().addAll("Option 1", "Option 2", "Option 2");


        //Button
        Button buttonGenerate = new Button("Generieren");
        GridPane.setConstraints(buttonGenerate, 1, 6);


        //Layout der Seite
        GridPane layoutGenerate = new GridPane();
        layoutGenerate.setPadding(new Insets(10, 10, 10, 10));
        layoutGenerate.setVgap(7);
        layoutGenerate.setHgap(10);
        layoutGenerate.getChildren().addAll(labelRace, labelClass, labelJob, labelLevel, cbRace, cbClass, cbJob, cbLevel, buttonGenerate);
        generatePage = new Scene(layoutGenerate, 300, 400);






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
        window.setScene(generatePage);
        window.show();
    }
}
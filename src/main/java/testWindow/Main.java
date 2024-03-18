package testWindow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    Stage window;
    Scene page1, page2;
    String name,klasse,rasse;


    public static void main(String[] args){
        launch(args);
    }


    //Stage -> Ist ein Fenster
    //Scene -> Ist der Inhalt des Fensters
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Title of the Window");

        //Seite 1 - Labels
        Label label1 = new Label("Das ist die erste Seite");
        GridPane.setConstraints(label1,0,0);

        Label label1Name = new Label("Name:");
        GridPane.setConstraints(label1Name,0,1);
        Label label1Klasse = new Label("Klasse:");
        GridPane.setConstraints(label1Klasse,0,2);
        Label label1Rasse = new Label("Ethnie:");
        GridPane.setConstraints(label1Rasse,0,3);


        //Seite 1 - Text Input
        TextField nameInput = new TextField("Max Muster");
        GridPane.setConstraints(nameInput,1,1);

        TextField klasseInput = new TextField();
        klasseInput.setPromptText("Paladin");
        GridPane.setConstraints(klasseInput,1,2);

        TextField rasseInput = new TextField();
        GridPane.setConstraints(rasseInput,1,3);


        //Seite 2 - Labels
        Label label2Name = new Label();
        Label label2Klasse = new Label();
        Label label2Rasse = new Label();


        //Man kann den Fenstertitel auf unterschiedliche weise festlegen
        Button button1 = new Button("Button Title");
        button1.setText("Zu Seite 2");
        GridPane.setConstraints(button1,1,4);

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


        //Layout 1 - Kinder sind in Vertikalen Spalten
        VBox layout1 = new VBox(20);
        //layout1.getChildren().addAll(label1,button1);
        //page1 = new Scene(layout1,200,200);
        layout1.getChildren().addAll(label2Name,label2Klasse,label2Rasse,button2);
        page2 = new Scene(layout1,300,400);



        //Layout 2 - Kinder sind auf Stapel/Stack
        StackPane layout2 = new StackPane();
        //layout2.getChildren().add(button2);
        //page2 = new Scene(layout2,600,300);


        //Layout 3 - Kinder sind in Tabelle/Grid
        GridPane layout3 = new GridPane();
        layout3.setPadding(new Insets(10, 10, 10, 10));
        layout3.setVgap(7);
        layout3.setHgap(10);
        layout3.getChildren().addAll(label1,label1Klasse,label1Name,label1Rasse,nameInput,klasseInput,rasseInput,button1);
        page1 = new Scene(layout3,300,400);





        //Es Startet auf Seite 1
        window.setScene(page1);
        window.show();




        /**
        //StackPane ist ein Stapel, button wird zentriert oben auf den Stapel gelegt
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        //Scene mit der Größe 300x250 mit Inhalt StackPane, Scene der Stage hinzufügen und anzeigen
        Scene scene = new Scene(layout, 300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
         **/
    }
}
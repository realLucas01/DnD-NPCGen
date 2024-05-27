package DnDGen.UI;


import DnDGen.TSWP1.RandomGenerator;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import org.springframework.context.ApplicationContext;
import DnDGen.DB.DBMS;
import DnDGen.TSWP1.RandomNPC;
import DnDGen.TSWP1.RandomGenerator;


//import java.awt.Button;
//import java.awt.Insets;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.stream.Collectors;

public class UI extends Application {
    Stage window;
    Scene generatePage;
    String name;
    VBox descriptionVBox;

	/* @author Lucas
	 * 
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer
	 */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);

    
    public static void main(String[] args) {
        launch(args);
    }


    //Stage -> Ist ein Fenster
    //Scene -> Ist der Inhalt des Fensters
    /* @author Marie
     *
     * Funktion die als erste ausgeführt wird. In ihr werden alle anderen Methoden aufgerufen.
     *
     * @param primaryStage: das Fenster das verwendet wird.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	window = primaryStage;
        window.setTitle("DnD NPC Generator");
        RandomNPC rNPC = new RandomNPC();
        RandomGenerator rGenerator = new RandomGenerator();

        ElementHistory elementHistory = new ElementHistory(window, dbms);

        GridPane layoutGenerateGrid = new GridPane();
        layoutGenerateGrid.setPadding(new Insets(10, 10, 10, 10));
        layoutGenerateGrid.setVgap(7);
        layoutGenerateGrid.setHgap(10);
        layoutGenerateGrid.setMinWidth(300);



        //Menü
        Menu menu = new Menu("Menu 1");
        MenuBar menuBar = new MenuBar();
        
        MenuItem menuItem1 = new MenuItem("Item 1");
        MenuItem menuItem2 = new MenuItem("Item 2");
        MenuItem menuItemHistory = new MenuItem("History");


        menuItem1.setOnAction(e -> {
            e.consume();
            layoutGenerateGrid.setVisible(false);
        });

        menuItem2.setOnAction(e -> {
            e.consume();
            layoutGenerateGrid.setVisible(true);
        });


        menuItemHistory.setOnAction(e -> {
            e.consume();
            window.setScene(elementHistory.getScene());
        });




        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);
        menu.getItems().add(menuItemHistory);

        menuBar.getMenus().add(menu);



        //Seite generatePage
        //Name
        Label labelName = new Label("Name:");
        GridPane.setConstraints(labelName, 0, 0);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        GridPane.setConstraints(nameField,1,0,2,1);

/*
          0 1 2
        0 - --- Name   00 1021
        1 - --- Rasse  01 1121
        2 - --- Klasse 02 1221
        3 - --- Job    03 1321
        */
        //Rasse
        Label labelRace = new Label("Rasse:");
        GridPane.setConstraints(labelRace, 0, 1);

        final ComboBox cbRace = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbRace, 1, 1,2,1);
        ArrayList<String> list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        cbRace.setMaxWidth(300);
        cbRace.getSelectionModel().selectFirst();



        //Klasse
        Label labelClass = new Label("Klasse:");
        GridPane.setConstraints(labelClass, 0, 2);

        final ComboBox cbClass = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbClass, 1, 2,2,1);
        list.clear();
        list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        cbClass.setMaxWidth(300);
        cbClass.getSelectionModel().selectFirst();


        //Job
        Label labelJob = new Label("Beruf:");
        GridPane.setConstraints(labelJob, 0, 3);

        final ComboBox cbJob = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbJob, 1, 3,2,1);
        list.clear();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        cbJob.setMaxWidth(300);
        cbJob.getSelectionModel().selectFirst();


        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 0, 4);

        final ComboBox cbLevel = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 1, 4,2,1);
        for(int i = 1; i <= 20; i++) {cbLevel.getItems().add(i);}
        cbLevel.setMaxWidth(300);
        cbLevel.getSelectionModel().selectFirst();

        //AbilityScore
        Label labelAbSc = new Label("Ability Score:");
        GridPane.setConstraints(labelAbSc, 0, 5);

        final ComboBox cbAbSc = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbAbSc, 1, 5,2,1);
        cbAbSc.getItems().addAll(dbms.onlyColumn("abilisc"));
        cbAbSc.setMaxWidth(300);
        cbAbSc.getSelectionModel().selectFirst();

        //Geschlecht
        final ToggleGroup groupSex = new ToggleGroup();
        Image img = new Image("ui-image/male-and-female.png");
        RadioButton rbRandom = new RadioButton("Zufall");
        rbRandom.setUserData("zufall");
        rbRandom.setToggleGroup(groupSex);
        GridPane.setConstraints(rbRandom, 0, 7,3,1);
        ImageView imgVMaleFemale = new ImageView(img);
        imgVMaleFemale.setFitWidth(32);
        imgVMaleFemale.setFitHeight(32);
        rbRandom.setGraphic(imgVMaleFemale);
        rbRandom.setSelected(true);

        img = new Image("ui-image/male.png");
        RadioButton rbMale = new RadioButton("Männlich");
        rbMale.setUserData("male");
        rbMale.setToggleGroup(groupSex);
        GridPane.setConstraints(rbMale, 0, 8,3,1);
        ImageView imgVMale = new ImageView(img);
        imgVMale.setFitWidth(32);
        imgVMale.setFitHeight(32);
        rbMale.setGraphic(imgVMale);

        img = new Image("ui-image/female.png");
        RadioButton rbFemale = new RadioButton("Weiblich");
        rbFemale.setUserData("female");
        rbFemale.setToggleGroup(groupSex);
        GridPane.setConstraints(rbFemale, 0, 9,3,1);
        ImageView imgVFemale = new ImageView(img);
        imgVFemale.setFitWidth(32);
        imgVFemale.setFitHeight(32);
        rbFemale.setGraphic(imgVFemale);


        //Spaltenbreite der Tabelle definieren
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(33);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(33);
        ColumnConstraints col3Constraints = new ColumnConstraints();
        col3Constraints.setPercentWidth(33);
        layoutGenerateGrid.getColumnConstraints().addAll(col1Constraints, col2Constraints, col3Constraints);

        ScrollPane npcScrollPane = new ScrollPane();
        npcScrollPane.setMinWidth(350);

        npcScrollPane.setVisible(false);


        //Button
        Button buttonGenerate = new Button("Generieren");
        GridPane.setConstraints(buttonGenerate, 0, 13,2,3);

        buttonGenerate.setStyle("-fx-font: bold italic 20pt \"Arial\"; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");

        HBox gridNpc = new HBox();


//    Button Klick
        buttonGenerate.setOnAction(e -> {
            ArrayList<Object> npc = rNPC.createRandomNPC(zufallName(nameField.getText()),zufall(cbRace),zufall(cbClass),zufall(cbJob), zufallLevel(cbLevel),
                                    zufall(cbAbSc),getSex(groupSex.getSelectedToggle().getUserData().toString()));

            if(gridNpc.getChildren().size() > 2) {
                gridNpc.getChildren().remove(2);
            }


            ElementNPC elementNPC = new ElementNPC(npc);

            elementNPC.getDescriptionFromNPC();
            descriptionVBox = elementNPC.getDescriptionVBox();
            descriptionVBox.setMinWidth(300);
            descriptionVBox.setMaxWidth(330);

            elementNPC.makeFillNPCStack();
            VBox npcStack = elementNPC.getNpcVBox();

            layoutGenerateGrid.getChildren().add(npcStack);
            npcScrollPane.setContent(npcStack);
            
            //Liste mit Positionen vom NPC
            /*
            System.out.println(npc);
            for(int i = 0; i < npc.size(); i++) {
                System.out.println(i + " - " + npc.get(i));
            }
            */

            //Trennlinien auf breite anpassen
            for(Line line: elementNPC.getLineList()) {
                line.setEndX(npcScrollPane.getWidth()-20);
            }

            //Den Generireten Inhalt sichtbar machen
            descriptionVBox.setVisible(true);
            npcStack.setVisible(true);
            npcScrollPane.setVisible(true);
            gridNpc.getChildren().add(descriptionVBox);
        });



        //Layout der Seite

        layoutGenerateGrid.getChildren().addAll(nameField, labelRace, labelClass, labelJob, labelLevel, labelAbSc, cbRace, cbClass,
                                                cbJob, cbLevel, cbAbSc,buttonGenerate, rbMale, rbFemale, rbRandom, labelName);
        VBox layoutGenerateVBox = new VBox(20);


        gridNpc.getChildren().addAll(layoutGenerateGrid, npcScrollPane);
        layoutGenerateVBox.getChildren().addAll(menuBar,gridNpc);

        generatePage = new Scene(layoutGenerateVBox, 1000, 600);




        //Es startet auf der generieren Seite
        //window.setScene(npcPage);
        generatePage.getStylesheets().add("/styles.css");
        window.setScene(generatePage);
        window.show();

        elementHistory.menuBar(generatePage);

        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            /*
            layoutGenerateGrid.setPrefWidth(generatePage.getWidth()/4);
            npcStack.setPrefWidth(generatePage.getWidth()/2);
            descriptionStack.setPrefWidth(generatePage.getWidth()/4);
            */
           // descriptionVBox.setMinWidth(generatePage.getWidth()/3);
            layoutGenerateGrid.setMinWidth(generatePage.getWidth()/3);
            npcScrollPane.setMinWidth(generatePage.getWidth()/3);
            npcScrollPane.setFitToWidth(true);
           // featuresGrid.setPrefWidth(npcScrollPane.getWidth()-20);
            /*
            line1.setEndX(npcScrollPane.getWidth()-20);
            line2.setEndX(npcScrollPane.getWidth()-20);
            line3.setEndX(npcScrollPane.getWidth()-20);
            line4.setEndX(npcScrollPane.getWidth()-20);
            line5.setEndX(npcScrollPane.getWidth()-20);
            */

        });



    }


    /* @author Marie
     *
     * Überprüft eine ComboBox, ob das Item "zufall" ausgewählt ist,
     * wenn, dann wird null zurückgegeben.
     *
     * @param cb: die ComboBox die geprüft wird.
     * @return Beim Item "zufall" kommt null zurück, sonst das Item.
     */
    public String zufall(ComboBox cb) {
        if(cb.getValue() == "zufall") {
            return null;
        } else {
            return cb.getValue().toString();
        }
    }

    //TIP wie Zufall nur speziell für das Level
    public Integer zufallLevel(ComboBox cb) {
        if(cb.getValue() == "zufall") {
            return null;
        } else {
            return Integer.parseInt(cb.getValue().toString());
        }
    }

    public String zufallName(String string) {
        if((string.equals("zufall")) || (string.equals(""))) {
            return null;
        }
        else return string;
    }

    /* @author Marie
     *
     * Teilt einen String der die Form eines Array hat in einzelne Elemente und gibt sie als Liste zurück
     *
     * @param string: ein String der als Liste geschrieben werden soll.
     * @return Eine Liste mit dem Inhalt von dem string eingabe.
     */
    public List stringToList(String string) {
        List list = new ArrayList<>();
        if(string.contains("[")) {
            string = string.substring(1,string.length()-1);
        }
        string = string.trim();

        if(string.isEmpty()) {return list;}

        string = string.concat(",");

        while(string.contains(",")) {
            list.add(string.substring(0,string.indexOf(",")).trim());
            string = string.substring(string.indexOf(",")+1);
        }

        return list;
    }

    /* @author Marie
     *
     * gibt den AbilityScore mit Modifier zurück. Für die Ability.
     *
     * @param index: der index gibt an welche Ability es ist. (Str, Dex, Con, Int, Wis, Cha)
     * @param npc: ein Array welches die gesamten Daten des NPC enthält
     * @return ein String bestehen aus dem AbilityScore + oder - und dem Modifier
     */
    public String abilityString(int index, ArrayList<String> npc) {
        String string = "";
        List listNpc = stringToList(npc.get(4));
        List listAbilityModifier = stringToList(npc.get(5));

        if(listAbilityModifier.get(index).toString().length() > 1) {
            string = listNpc.get(index) + listAbilityModifier.get(index).toString();
        } else {string = listNpc.get(index) + "+" + listAbilityModifier.get(index);}

        return string;
    }

    // Gibt den Inhalt von vom String zurück, außer wenn Zufall, dann null
    public String getSex(String sex) {
        if(sex.equals("zufall")) {
            return null;
        }
        return sex;
    }

    // Gibt das richtige Pronoun(his, her) zum sex wieder
    public String getPronoun(String sex) {
        if(sex.equals("male")) {
            return "his";
        } else {
            return "her";
        }
    }

    // Gibt das richtige possessiv Pronoun(he, she) zum sex wieder
    public String getPossessivePronoun(String sex) {
        if(sex.equals("male")) {
            return "he";
        } else {
            return "she";
        }
    }

    // macht den ersten Buchstaben groß
    public String firstLetterBig(String word) {
        String big = word.substring(0,1).toUpperCase();
        big = big.concat(word.substring(1));
        return big;
    }

    // macht den ersten Buchstaben klein
    public String firstLetterSmall(String word) {
        String small = word.substring(0,1).toLowerCase();
        small = small.concat(word.substring(1));
        return small;
    }
}
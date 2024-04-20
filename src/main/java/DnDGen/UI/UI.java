package DnDGen.UI;


import org.springframework.context.ApplicationContext;
import DnDGen.DB.DBMS;
import DnDGen.TSWP1.RandomNPC;


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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UI extends Application {
    Stage window;
    Scene generatePage, npcPage, historyPage, editPage;
    String name, klasse, rasse;
    
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

        menuItem1.setOnAction(e -> {
            e.consume();
            layoutGenerateGrid.setVisible(false);
        });

        menuItem2.setOnAction(e -> {
            e.consume();
            layoutGenerateGrid.setVisible(true);
        });


        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);

        menuBar.getMenus().add(menu);


        //Seite generatePage
        //Name
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        GridPane.setConstraints(nameField,1,1,2,1);

        //Rasse
        Label labelRace = new Label("Rasse:");
        GridPane.setConstraints(labelRace, 1, 2);

        final ComboBox cbRace = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbRace, 2, 2);
        ArrayList<String> list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        cbRace.getSelectionModel().selectFirst();


        //Klasse
        Label labelClass = new Label("Klasse:");
        GridPane.setConstraints(labelClass, 1, 3);

        final ComboBox cbClass = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbClass, 2, 3);
        list.clear();
        list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        cbClass.getSelectionModel().selectFirst();


        //Job
        Label labelJob = new Label("Beruf:");
        GridPane.setConstraints(labelJob, 1, 4);

        final ComboBox cbJob = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbJob, 2, 4);
        list.clear();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        cbJob.getSelectionModel().selectFirst();


        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 1, 5);

        final ComboBox cbLevel = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 2, 5);
        for(int i = 1; i < 20; i++) {cbLevel.getItems().add(i);}
        cbLevel.getSelectionModel().selectFirst();

        //AbilityScore
        Label labelAbSc = new Label("Ability Score:");
        GridPane.setConstraints(labelAbSc, 1, 6);

        final ComboBox cbAbSc = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbAbSc, 2, 6);
        cbAbSc.getItems().addAll(dbms.onlyColumn("abilisc"));
        cbAbSc.getSelectionModel().selectFirst();




        //Als Textfeld

        //Als Tabelle/einzelne Bausteine
        VBox npcStack = new VBox();
        //GridPane.setConstraints(npcStack,4,0,4,25);

        //Name, Klasse, Rasse, Alignment
        GridPane nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(4);
        nameClassGrid.setHgap(25);

        Label nameL      = new Label("Name: ");
        Label levelL     = new Label("Level: ");
        Label classL     = new Label("Klasse: ");
        Label jobL       = new Label("Job: ");
        Label raceL      = new Label("Rasse: ");
        Label alignmentL = new Label("Alignment: ");
        Label ageL       = new Label("Alter: ");
        Label sexL       = new Label("Geschlecht: ");
        Label deityL     = new Label("Gottheit: ");

        GridPane.setConstraints(nameL,0,0);
        GridPane.setConstraints(levelL,1,0);
        GridPane.setConstraints(classL,0,1,2,1);
        GridPane.setConstraints(raceL,0,2);
        GridPane.setConstraints(jobL,1,2);
        GridPane.setConstraints(deityL,0,3);
        GridPane.setConstraints(alignmentL,1,3);
        GridPane.setConstraints(ageL,0,4);
        GridPane.setConstraints(sexL,1,4);

        nameClassGrid.getChildren().addAll(nameL, classL, jobL, raceL, alignmentL, ageL, sexL, deityL, levelL);

        //Armor class, Hit points, Speed
        VBox armorHitSpeedPane = new VBox();
        armorHitSpeedPane.setSpacing(4);
        Label armorClassL = new Label("Rüstungsklasse: ");
        Label hitPointsL  = new Label("Trefferpunkte: ");
        Label speedL      = new Label("Geschwindigkeit: ");
        Label languagesL   = new Label("Sprachen: ");

        armorHitSpeedPane.getChildren().addAll(armorClassL,hitPointsL,speedL, languagesL);
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

        //Inventar (Waffen & Rüstung)
        GridPane inventoryGrid = new GridPane();
        inventoryGrid.setPadding(new Insets(5, 10, 10, 10));
        inventoryGrid.setVgap(1);
        inventoryGrid.setHgap(25);

        Label armorTextL      = new Label("Rüstung: ");
        Label weaponTextL     = new Label("Waffen: ");
        Label armorL      = new Label("Rüstung: ");
        Label weaponL     = new Label("Waffen: ");

        GridPane.setConstraints(armorTextL,0,0);
        GridPane.setConstraints(weaponTextL,1,0);
        GridPane.setConstraints(armorL,0,1);
        GridPane.setConstraints(weaponL,1,1);

        inventoryGrid.getChildren().addAll(armorL, weaponL, armorTextL, weaponTextL);

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


        Line line1 = new Line(0.0f,0.0f,300.0f,0.0f);
        Line line2 = new Line(0.0f,0.0f,300.0f,0.0f);
        Line line3 = new Line(0.0f,0.0f,300.0f,0.0f);
        Line line4 = new Line(0.0f,0.0f,300.0f,0.0f);
        Line line5 = new Line(0.0f,0.0f,300.0f,0.0f);

        npcStack.getChildren().addAll(nameClassGrid,line1,armorHitSpeedPane,line2,statsBox,line3, inventoryGrid, line5,skillSensesBox,line4,spellActionBox);
        npcStack.setVisible(false);

        VBox descriptionStack = new VBox();
        descriptionStack.setVisible(false);
        //layoutGenerateGrid.getChildren().add(npcStack);





        //Button
        Button buttonGenerate = new Button("Generieren");
        GridPane.setConstraints(buttonGenerate, 1, 7);

        buttonGenerate.setStyle("-fx-font: bold italic 20pt \"Arial\"; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");

        buttonGenerate.setOnAction(e -> {
           //Parameter für generieren  (zufall(cbRace),zufall(cbClass),zufall(cbJob),zufall(cbLevel))
            ArrayList<String> npc = rNPC.createRandomNPC(zufall(cbRace),zufall(cbClass),zufall(cbJob), zufallLevel(cbLevel),null,null);
            //  0      1     2     3        4                                               5                                       6    7     8      9        10          11     12      13        14    15    16          17       18       19
            //(race, level, job, class, AbilityScore(Str, Dex, Con, Int, Wis, Cha), AbilityModifier(Str, Dex, Con, Int, Wis, Cha), HP, Armor, AC, Weapon(),savingThrows, Speed, Skills(), Gender, Age, Name, Description, Alignment, Deity, Languages())
            //-------------------------                                                                                           ---         --            --------  ------  ---  ----               ------------------------------


            /*
            System.out.println(npc);
            System.out.println("Rasse: " + npc.get(0));
            System.out.println("Level: " + String.valueOf(npc.get(1)));
            System.out.println("Job: " + npc.get(2));
            System.out.println("Klasse: " + npc.get(3));
            System.out.println("AbilityScore: " + npc.get(4));
            System.out.println("AbilityModifier: " + npc.get(5));
            System.out.println("HP: " + String.valueOf(npc.get(6)));
            System.out.println("Armor: " + npc.get(7));
            System.out.println("AC: " + String.valueOf(npc.get(8)));
            System.out.println("Weapons(): " + npc.get(9));
            System.out.println("Speed: " + npc.get(10));
            System.out.println("Saving Throws: " + npc.get(11));
            System.out.println("Skills: " + npc.get(12));
            System.out.println("Sex: " + npc.get(13));
            System.out.println("Alter: " + String.valueOf(npc.get(14)));
            System.out.println("Name: " + npc.get(15));
            System.out.println("Beschreibung: " + npc.get(16));
            System.out.println("Alignment: " + npc.get(17));
            System.out.println("Gottheit: " + npc.get(18));
            System.out.println("Sprache: " + npc.get(19));
/*
            String languages = "";
            List lang = stringToList(npc.get(17));
            for(int i = 0; i < lang.size(); i++) {
                languages = languages.concat(lang.get(i) + "\n");
            }
            System.out.println("Sprachen Schleife: " + npc.get(18));

            */





            String stringNpc = "";
            List listNpc = new ArrayList<>();

            nameL.setText("Name: " + npc.get(15));
            classL.setText("Klasse: " + npc.get(3));
            raceL.setText("Rasse: " + npc.get(0));
            levelL.setText("Level: " + String.valueOf(npc.get(1)));
            jobL.setText("Beruf: " + npc.get(2));
            alignmentL.setText("Alignment: " + npc.get(16));
            ageL.setText("Alter: " + String.valueOf(npc.get(14)));
            sexL.setText("Geschlecht: " + npc.get(13));
            deityL.setText("Gottheit: " + npc.get(18));

            armorClassL.setText("Rüstungsklasse: " + String.valueOf(npc.get(8)));
            hitPointsL.setText("Trefferpunkte: " + String.valueOf(npc.get(6)));
            speedL.setText("Geschwindigkeit: " + String.valueOf(npc.get(10)));


            //Sprachen
            stringNpc = "";
            listNpc.clear();
            listNpc = stringToList(npc.get(19));
            for(int i = 0; i < listNpc.size(); i++) {
                stringNpc = stringNpc.concat("    - " + listNpc.get(i) + "\n");
            }
            languagesL.setText("Sprachen:\n" + stringNpc);


            //Ability
            strL.setText(" Str\n" + abilityString(0,npc));
            dexL.setText(" Dex\n" + abilityString(1,npc));
            conL.setText(" Con\n" + abilityString(2,npc));
            intL.setText(" Int\n" + abilityString(3,npc));
            wisL.setText(" Wis\n" + abilityString(4,npc));
            chaL.setText(" Cha\n" + abilityString(5,npc));


            //Inventar
            stringNpc = "";
            listNpc.clear();
            listNpc = stringToList(npc.get(9));
            for(int i = 0; i < listNpc.size(); i++) {
                stringNpc = stringNpc.concat("    - " + listNpc.get(i) + "\n");
            }
            weaponL.setText(stringNpc);

            armorL.setText("    - " + npc.get(7));


            //Spell
            /*
            Text skills = new Text(npc.get(10));
            skillTF.getChildren().add(skills);
*/
            /*
            Text senses = new Text(npc.get(10));
            senseTF.getChildren().add(senses);

            Text spells = new Text(npc.get(10));
            spellTF.getChildren().add(spells);

            Text action = new Text(npc.get(10));
            actionTF.getChildren().add(action);
            */


            //Beschreibung
            //descriptionStack.getChildren().removeAll();

            descriptionStack.getChildren().clear();
            listNpc = stringToList(npc.get(17));
            Label titel = new Label("Beschreibung: ");
            for(int i = 0; i < listNpc.size(); i++) {
                Label label = new Label(listNpc.get(i).toString());
                label.setWrapText(true);
                descriptionStack.getChildren().add(label);
            }



/*

           System.out.println(zufall(cbRace));
           System.out.println(zufall(cbClass));
           System.out.println(zufall(cbJob));
           System.out.println(zufallLevel(cbLevel));
           
 */
           
          // nameL.setText("Name: " + rNPC.createRandomNPC(zufall(cbRace),zufall(cbClass),zufall(cbJob), zufallLevel(cbLevel),null,"male"));

        npcStack.setVisible(true);
        descriptionStack.setVisible(true);



        });













        //Layout der Seite

        layoutGenerateGrid.getChildren().addAll(nameField, labelRace, labelClass, labelJob, labelLevel, labelAbSc, cbRace, cbClass,
                                                cbJob, cbLevel, cbAbSc,buttonGenerate);
        VBox layoutGenerateVBox = new VBox(20);
        HBox gridNpc = new HBox();

        gridNpc.getChildren().addAll(layoutGenerateGrid, npcStack, descriptionStack);
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
        generatePage.getStylesheets().add("/styles.css");
        window.setScene(generatePage);
        window.show();

        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            layoutGenerateGrid.setPrefWidth(generatePage.getWidth()/4);
            npcStack.setPrefWidth(generatePage.getWidth()/2);
            descriptionStack.setPrefWidth(generatePage.getWidth()/4);

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

    public Integer zufallLevel(ComboBox cb) {
        if(cb.getValue() == "zufall") {
            return null;
        } else {
            return Integer.parseInt(cb.getValue().toString());
        }
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
}
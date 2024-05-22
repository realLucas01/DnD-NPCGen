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

        ElementHistory elementHistory = new ElementHistory(window);

        GridPane layoutGenerateGrid = new GridPane();
        layoutGenerateGrid.setPadding(new Insets(10, 10, 10, 10));
        layoutGenerateGrid.setVgap(7);
        layoutGenerateGrid.setHgap(10);
        layoutGenerateGrid.setMinWidth(300);



        //Menü
        Menu menu = new Menu("Menu 1");
        MenuBar menuBar = new MenuBar();

        menu.setOnShowing(e -> { System.out.println("Showing Menu 1"); });
        menu.setOnShown  (e -> { System.out.println("Shown Menu 1"); });
        menu.setOnHiding (e -> { System.out.println("Hiding Menu 1"); });
        menu.setOnHidden (e -> { System.out.println("Hidden Menu 1"); });

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

        //Armor class, Hit points, Speed, Language, Feature
        GridPane acHpSpLangGrid = new GridPane();
        acHpSpLangGrid.setPadding(new Insets(5, 5, 5, 5));
        acHpSpLangGrid.setVgap(4);
        acHpSpLangGrid.setHgap(5);
        Label armorClassL = new Label("Rüstungsklasse: ");
        GridPane.setConstraints(armorClassL,0,0);
        Label hitPointsL  = new Label("Trefferpunkte: ");
        GridPane.setConstraints(hitPointsL,0,1);
        Label speedL      = new Label("Geschwindigkeit: ");
        GridPane.setConstraints(speedL,0,2);
        Label languagesL   = new Label("Sprachen: ");
        GridPane.setConstraints(languagesL,0,4);
        acHpSpLangGrid.getChildren().addAll(armorClassL,hitPointsL,speedL,languagesL);

        //Features
        GridPane featuresGrid = new GridPane();
        featuresGrid.setPadding(new Insets(5,5,5,5));
        featuresGrid.setVgap(4);
        featuresGrid.setHgap(5);
        Label actionL = new Label("Aktionen: ");
        GridPane.setConstraints(actionL,0,0);
        Label bonusActionL = new Label("Bonus Aktion: ");
        GridPane.setConstraints(bonusActionL,0,1);
        Label bonusTextL = new Label("Bonus Text: ");
        GridPane.setConstraints(bonusTextL,0,2);

        ColumnConstraints featureGridColCon1 = new ColumnConstraints();
        featureGridColCon1.setPercentWidth(30);
        ColumnConstraints featureGridColCon2 = new ColumnConstraints();
        featureGridColCon2.setPercentWidth(70);
        featuresGrid.getColumnConstraints().addAll(featureGridColCon1, featureGridColCon2);

        featuresGrid.getChildren().addAll(actionL, bonusActionL, bonusTextL);


        //Stats
        GridPane statsGrid = new GridPane();
        statsGrid.setPadding(new Insets(5, 5, 5, 5));
        statsGrid.setVgap(4);
        statsGrid.setHgap(5);

        Label abilityScore = new Label("Ability Score: ");
        GridPane.setConstraints(abilityScore,0,1);
        Label savingThrows = new Label("Saving Throws: ");
        GridPane.setConstraints(savingThrows, 0,2);

        statsGrid.getChildren().addAll(abilityScore,savingThrows);


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
        HBox skillSensesBox = new HBox();
        TextFlow skillTF = new TextFlow();
        Text text1=new Text("Skills:\n");
        text1.setStyle("-fx-font-weight: bold");
        skillTF.getChildren().addAll(text1);

        VBox spellTF = new VBox();
        Text text3=new Text("Spells:\n");
        text3.setStyle("-fx-font-weight: bold");
        spellTF.getChildren().addAll(text3);

        skillSensesBox.getChildren().addAll(skillTF,spellTF);
        skillSensesBox.setPadding(new Insets(10, 10, 10, 10));

        //Spell, Action
        VBox spellActionBox = new VBox();
        /*
        TextFlow spellTF = new TextFlow();
        text1 = new Text("Zauber: ");
        text1.setStyle("-fx-font-weight: bold");
        spellTF.getChildren().addAll(text1);
        */

        TextFlow actionTF = new TextFlow();
        text3 = new Text("Aktionen:\n");
        text3.setStyle("-fx-font-weight: bold");
        actionTF.getChildren().addAll(text3);

        spellActionBox.getChildren().addAll(actionTF);
        spellActionBox.setPadding(new Insets(10, 10, 10, 10));

        ScrollPane npcScrollPane = new ScrollPane(npcStack);
        npcScrollPane.setMinWidth(350);

        Line line1 = new Line(0.0f,0.0f,npcScrollPane.getWidth(),0.0f);
        Line line2 = new Line(0.0f,0.0f,npcScrollPane.getWidth(),0.0f);
        Line line3 = new Line(0.0f,0.0f,npcScrollPane.getWidth(),0.0f);
        Line line4 = new Line(0.0f,0.0f,npcScrollPane.getWidth(),0.0f);
        Line line5 = new Line(0.0f,0.0f,300.0f,0.0f);

        npcStack.getChildren().addAll(nameClassGrid,line1,acHpSpLangGrid,line2,statsGrid,line3, inventoryGrid, line5,skillSensesBox,line4,featuresGrid);
        npcStack.setVisible(false);

        /*


        */
        npcScrollPane.setVisible(false);

        //layoutGenerateGrid.getChildren().add(npcStack);

        //Button
        Button buttonGenerate = new Button("Generieren");
        GridPane.setConstraints(buttonGenerate, 0, 13,2,3);

        buttonGenerate.setStyle("-fx-font: bold italic 20pt \"Arial\"; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");

        HBox gridNpc = new HBox();


//    Button Klick
        buttonGenerate.setOnAction(e -> {
            ArrayList<String> npc = rNPC.createRandomNPC(zufallName(nameL),zufall(cbRace),zufall(cbClass),zufall(cbJob), zufallLevel(cbLevel),
                                    zufall(cbAbSc),getSex(groupSex.getSelectedToggle().getUserData().toString()));

            if(gridNpc.getChildren().size() > 2) {
                gridNpc.getChildren().remove(2);
            }


            ElementNPC elementNPC = new ElementNPC(npc);

            elementNPC.getDescriptionFromNPC();
            descriptionVBox = elementNPC.getDescriptionVBox();
            descriptionVBox.setMinWidth(300);
            descriptionVBox.setMaxWidth(330);

            
            //Liste mit Positionen vom NPC
            /*
            System.out.println(npc);
            for(int i = 0; i < npc.size(); i++) {
                System.out.println(i + " - " + npc.get(i));
            }
            */

            String stringNpc = "";
            List listNpc = new ArrayList<>();

            nameL.setText("Name: " + npc.get(15));
            name = npc.get(15);
            classL.setText("Klasse: " + npc.get(3));
            raceL.setText("Rasse: " + npc.get(0));
            levelL.setText("Level: " + String.valueOf(npc.get(1)));
            jobL.setText("Beruf: " + npc.get(2));
            alignmentL.setText("Alignment: " + npc.get(16));
            ageL.setText("Alter: " + String.valueOf(npc.get(14)));
            sexL.setText("Geschlecht: " + npc.get(13));
            deityL.setText("Gottheit: " + npc.get(18));

            //AmorClass, HitPoints, Speed, Languages
            acHpSpLangGrid.getChildren().remove(4,acHpSpLangGrid.getChildren().size());

            Label aCL = new Label(String.valueOf(npc.get(8)));
            GridPane.setConstraints(aCL,1,0);
            Label hPL = new Label(String.valueOf(npc.get(6)));
            GridPane.setConstraints(hPL,1,1);
            acHpSpLangGrid.getChildren().addAll(aCL,hPL);

            //Geschwindigkeit
            listNpc.clear();
            listNpc = stringToList(npc.get(10));
            for(int i = 0; i < listNpc.size(); i++) {
                Label label = new Label(listNpc.get(i).toString());
                GridPane.setConstraints(label,1,i+2);
                acHpSpLangGrid.getChildren().add(label);
            }

            //Sprachen
            listNpc.clear();
            listNpc = stringToList(npc.get(19));
            for(int i = 0; i < listNpc.size(); i++) {
                Label label = new Label(listNpc.get(i).toString());
                GridPane.setConstraints(label,1,i+4);
                acHpSpLangGrid.getChildren().add(label);
            }


            //Features
            featuresGrid.getChildren().remove(3,featuresGrid.getChildren().size());
            ArrayList<String> featureList = rNPC.determineFeatures(npc.get(0),"action");
            VBox actionBox = new VBox();
            actionBox.setSpacing(5);
            GridPane.setConstraints(actionBox,1,0);
            for(int i = 0; i < featureList.size(); i++) {
                Label label = new Label(featureList.get(i));
                label.setWrapText(true);
                actionBox.getChildren().add(label);
            }

            featureList.clear();
            featureList = rNPC.determineFeatures(npc.get(0),"bonus action");
            VBox bonusActionBox = new VBox();
            bonusActionBox.setSpacing(5);
            GridPane.setConstraints(bonusActionBox,1,1);
            for(int i = 0; i < featureList.size(); i++) {
                Label label = new Label(featureList.get(i));
                label.setWrapText(true);
                bonusActionBox.getChildren().add(label);
            }

            featureList.clear();
            featureList = rNPC.determineFeatures(npc.get(0),"bonus text");
            VBox bonusTextBox = new VBox();
            bonusTextBox.setSpacing(5);
            GridPane.setConstraints(bonusTextBox,1,2);
            for(int i = 0; i < featureList.size(); i++) {
                Label label = new Label(featureList.get(i));
                label.setWrapText(true);
                bonusTextBox.getChildren().add(label);
            }
            featuresGrid.getChildren().addAll(actionBox,bonusActionBox,bonusTextBox);


            //Ability
            statsGrid.getChildren().remove(2,statsGrid.getChildren().size());
            ArrayList<String> abilityString = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));
            for(int i = 1; i <= abilityString.size(); i++) {
                Label label = new Label(abilityString.get(i-1));
                GridPane.setConstraints(label,i,0);
                statsGrid.getChildren().add(label);
            }
            for(int i = 1; i <= abilityString.size(); i++) {
                Label label = new Label(abilityString(i-1,npc));
                GridPane.setConstraints(label,i,1);
                statsGrid.getChildren().add(label);
            }
            listNpc.clear();
            listNpc = stringToList(npc.get(11));
            for(int j = 0; j < listNpc.size(); j++) {
                String ability = listNpc.get(j).toString().substring(0,3);
                String score = listNpc.get(j).toString().substring(4);
                for(int i = 1; i <= abilityString.size(); i++) {
                    if(abilityString.get(i-1).equals(ability)) {
                        Label label = new Label(score);
                        GridPane.setConstraints(label,i,2);
                        statsGrid.getChildren().add(label);
                    }
                }
            }


            //Inventar
            stringNpc = "";
            listNpc.clear();
            listNpc = stringToList(npc.get(9));
            for(int i = 0; i < listNpc.size(); i++) {
                stringNpc = stringNpc.concat("    - " + listNpc.get(i) + "\n");
            }
            weaponL.setText(stringNpc);
            weaponL.setWrapText(true);
            armorL.setText("    - " + npc.get(7));
            armorL.setWrapText(true);


            //Skills
            skillTF.getChildren().remove(1, skillTF.getChildren().size());
            listNpc.clear();
            listNpc = stringToList(npc.get(12));
            for(int i = 0; i < listNpc.size(); i++) {
                Text action = new Text("    - " + listNpc.get(i).toString() + "\n");
                skillTF.getChildren().add(action);
            }

        /*
            //Spells
            /*spellTF.getChildren().remove(1, spellTF.getChildren().size());

            List<String> abilityModifierList = stringToList(npc.get(5));
            List<String> spells = rGenerator.randSpellList(npc.get(3),abilityModifierList,Integer.parseInt(npc.get(1)));


            for(int i = 0; i < spells.size(); i++) {
                String spell = spells.get(i).replace('[',' ');
                spell = spell.replace(']',' ');
                Label spellText = new Label("    - " + spell);
                spellText.setWrapText(true);
                spellTF.getChildren().add(spellText);
            }

            //System.out.println(spells);
			*/
            //Beschreibung
            /*
            descriptionVBox.getChildren().clear();

            //Beschreibung kurz (direkten zugriff auf Methode von NPC)
            GridPane descriptionGrid = new GridPane();

            //Spaltenbreite der Tabelle definieren
            ColumnConstraints descriptionCol1Constraint = new ColumnConstraints();
            descriptionCol1Constraint.setPercentWidth(25);
            descriptionCol1Constraint.setMinWidth(65);
            ColumnConstraints descriptionCol2Constraint = new ColumnConstraints();
            descriptionCol2Constraint.setPercentWidth(75);
            descriptionGrid.getColumnConstraints().addAll(descriptionCol1Constraint,descriptionCol2Constraint);

            //Plothook nicht da Stelle nach attitute
            ArrayList<String> descriptionTitel = new ArrayList<>(Arrays.asList("Eyes: ","Hair color: ", "Hairstyle: ",
                                                                "Skin: ", "Trait: ", "Mannerism: ", "Bond: ", "Flaw: ",
                                                                "Ideal: ", "Voice: ", "Clothing: ", "Attitude: ",
                                                                "Job: ", "Gender: ", "Age: ", "Deity: ",
                                                                "Size: ", "Height: "));
            ArrayList<String> npcDescriptionArray = rGenerator.randDescription(npc.get(0),npc.get(16));
            for(int i = 0; i < npcDescriptionArray.size(); i++) {
                TextFlow descriptionTextNew = new TextFlow();
                String description = descriptionTitel.get(i);
                Text text = new Text(description);
                descriptionTextNew.getChildren().add(text);
                GridPane.setConstraints(descriptionTextNew,0,i);
                descriptionGrid.getChildren().add(descriptionTextNew);

                descriptionTextNew = new TextFlow();
                description = npcDescriptionArray.get(i);
                text = new Text(description);
                descriptionTextNew.getChildren().add(text);
                GridPane.setConstraints(descriptionTextNew,1,i);
                descriptionGrid.getChildren().add(descriptionTextNew);
            }
            descriptionVBox.getChildren().add(descriptionGrid);


            TextFlow descriptionTextNew = new TextFlow();
            String description = "\n\n";
            Text text = new Text(description);
            descriptionTextNew.getChildren().add(text);
            descriptionVBox.getChildren().add(descriptionTextNew);

            //Beschreibung lang (altes Modell / meine Methode für StringToList)
            //listNpc = stringToList(npc.get(17));
            listNpc = npcDescriptionArray;
            String sex = npc.get(13);
            TextFlow descriptionText = new TextFlow();
                description = "The skin color of " + name + " is " + listNpc.get(0) + ". " +
                    name + "'s eyes appear to be in a " + listNpc.get(1) + " shade. " +
                    firstLetterBig(getPossessivePronoun(sex)) + " wears " + getPronoun(sex) + " hair as " + listNpc.get(2) +
                    ". They have the color " + listNpc.get(3) + ". " + firstLetterBig(getPossessivePronoun(sex)) +
                    " is " + listNpc.get(4) + " and " + firstLetterSmall(listNpc.get(5).toString() + ". ");
            for(int i = 6; i < listNpc.size()-4; i++) {
                description = description.concat(" " + listNpc.get(i).toString());
            }

            description = description.concat(" " + firstLetterBig(getPossessivePronoun(sex)) + " is driven by " +
                    listNpc.get(listNpc.size()-4) + ". " + firstLetterBig(getPronoun(sex)) + " voice is " +
                    listNpc.get(listNpc.size()-3) + " " + name + " is wearing a " + listNpc.get(listNpc.size()-2) +
                    " and " + getPossessivePronoun(sex) + " is " + listNpc.get(listNpc.size()-1));

            text = new Text(description);
            text.wrappingWidthProperty().bind(descriptionVBox.widthProperty());
            descriptionText.getChildren().add(text);

            descriptionVBox.getChildren().add(descriptionText);
            */

            //Trennlinien auf breite anpassen
            line1.setEndX(npcScrollPane.getWidth()-20);
            line2.setEndX(npcScrollPane.getWidth()-20);
            line3.setEndX(npcScrollPane.getWidth()-20);
            line4.setEndX(npcScrollPane.getWidth()-20);
            line5.setEndX(npcScrollPane.getWidth()-20);


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
            featuresGrid.setPrefWidth(npcScrollPane.getWidth()-20);
            line1.setEndX(npcScrollPane.getWidth()-20);
            line2.setEndX(npcScrollPane.getWidth()-20);
            line3.setEndX(npcScrollPane.getWidth()-20);
            line4.setEndX(npcScrollPane.getWidth()-20);
            line5.setEndX(npcScrollPane.getWidth()-20);
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

    public String zufallName(Label label) {
        if((label.getText().equals("zufall")) || (label.getText().equals(""))) {
            return null;
        }
        else return label.getText();
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
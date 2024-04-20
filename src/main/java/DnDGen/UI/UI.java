package DnDGen.UI;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        cbJob.getSelectionModel().selectFirst();


        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 0, 4);

        final ComboBox cbLevel = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 1, 4,2,1);
        for(int i = 1; i < 20; i++) {cbLevel.getItems().add(i);}
        cbLevel.getSelectionModel().selectFirst();

        //AbilityScore
        Label labelAbSc = new Label("Ability Score:");
        GridPane.setConstraints(labelAbSc, 0, 5);

        final ComboBox cbAbSc = new ComboBox(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbAbSc, 1, 5,2,1);
        cbAbSc.getItems().addAll(dbms.onlyColumn("abilisc"));
        cbAbSc.getSelectionModel().selectFirst();

        //Geschlecht
        final ToggleGroup groupSex = new ToggleGroup();
        Image img = new Image("ui-image/male.png");
        RadioButton rbMale = new RadioButton("Männlich");
        rbMale.setUserData("male");
        rbMale.setToggleGroup(groupSex);
        GridPane.setConstraints(rbMale, 1, 7);
        rbMale.setGraphic(new ImageView(img));

        img = new Image("ui-image/female.png");
        RadioButton rbFemale = new RadioButton("Weiblich");
        rbFemale.setUserData("female");
        rbFemale.setToggleGroup(groupSex);
        GridPane.setConstraints(rbFemale, 2, 7);
        rbFemale.setGraphic(new ImageView(img));

        img = new Image("ui-image/male-and-female.png");
        RadioButton rbRandom = new RadioButton("Zufall");
        rbRandom.setUserData("zufall");
        rbRandom.setToggleGroup(groupSex);
        GridPane.setConstraints(rbRandom, 0, 7);
        rbRandom.setGraphic(new ImageView(img));
        rbRandom.setSelected(true);






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


        Line line1 = new Line(0.0f,0.0f,400.0f,0.0f);
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
        GridPane.setConstraints(buttonGenerate, 1, 8);

        buttonGenerate.setStyle("-fx-font: bold italic 20pt \"Arial\"; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");



//   Button Klick
      buttonGenerate.setOnAction(e -> {
          ArrayList<String> npc = rNPC.createRandomNPC(zufall(cbRace),zufall(cbClass),zufall(cbJob), zufallLevel(cbLevel),null,getSex(groupSex.getSelectedToggle().getUserData().toString()));
          name = nameField.getText();

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
          for(int i = 0; i < stringToList(npc.get(10)).size(); i++) {
              speedL.setText(speedL.getText().concat(stringToList(npc.get(10)).get(i) + " "));
          }
        //  speedL.setText("Geschwindigkeit: " + stringToList(npc.get(10)).get(0));

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
          weaponL.setWrapText(true);
          armorL.setText("    - " + npc.get(7));
          armorL.setWrapText(true);

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

          descriptionStack.getChildren().clear();
            /*array ->
            TextFlow descriptionText = new TextFlow();
            String description = "



           */


            listNpc = stringToList(npc.get(17));
            String sex = npc.get(13);

            TextFlow descriptionText = new TextFlow();
            String description = "The skin color of " + name + " is " + listNpc.get(0) + ". " +
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

            Text text = new Text(description);
            text.wrappingWidthProperty().bind(descriptionStack.widthProperty());
            descriptionText.getChildren().add(text);

            descriptionStack.getChildren().add(descriptionText);





            npcStack.setVisible(true);
            descriptionStack.setVisible(true);
        });



        //Layout der Seite

        layoutGenerateGrid.getChildren().addAll(nameField, labelRace, labelClass, labelJob, labelLevel, labelAbSc, cbRace, cbClass,
                                                cbJob, cbLevel, cbAbSc,buttonGenerate, rbMale, rbFemale, rbRandom, labelName);
        VBox layoutGenerateVBox = new VBox(20);
        HBox gridNpc = new HBox();

        gridNpc.getChildren().addAll(layoutGenerateGrid, npcStack, descriptionStack);
        layoutGenerateVBox.getChildren().addAll(menuBar,gridNpc);

        generatePage = new Scene(layoutGenerateVBox, 800, 600);




        //Es startet auf der generieren Seite
        //window.setScene(npcPage);
        generatePage.getStylesheets().add("/styles.css");
        window.setScene(generatePage);
        window.show();

        /*
        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            layoutGenerateGrid.setPrefWidth(generatePage.getWidth()/4);
            npcStack.setPrefWidth(generatePage.getWidth()/2);
            descriptionStack.setPrefWidth(generatePage.getWidth()/4);

        });
        */
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

    public String getSex(String sex) {
        if(sex.equals("zufall")) {
            return null;
        }
        return sex;
    }

    public String getPronoun(String sex) {
        if(sex.equals("male")) {
            return "his";
        } else {
            return "her";
        }
    }

    public String getPossessivePronoun(String sex) {
        if(sex.equals("male")) {
            return "he";
        } else {
            return "she";
        }
    }

    public String firstLetterBig(String word) {
        String big = word.substring(0,1).toUpperCase();
        big = big.concat(word.substring(1));
        return big;
    }

    public String firstLetterSmall(String word) {
        String small = word.substring(0,1).toLowerCase();
        small = small.concat(word.substring(1));
        return small;
    }
}
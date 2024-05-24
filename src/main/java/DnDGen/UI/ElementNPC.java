package DnDGen.UI;

import DnDGen.TSWP1.RandomGenerator;
import DnDGen.TSWP1.RandomNPC;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementNPC {
    VBox npcStack, descriptionVBox;
    ArrayList<String> npc;
    RandomNPC rNPC = new RandomNPC();
    RandomGenerator rGenerator = new RandomGenerator();
    ArrayList<Line> lineList;


    // Konstruktor
    ElementNPC(ArrayList<String> npc){
        this.npc = npc;
        npcStack = new VBox();
        descriptionVBox = new VBox();
        lineList = new ArrayList<>();
    }

    // gibt Beschreibung als Fließtext
    public void getDescriptionOld() {
        String name = npc.get(15);

        ArrayList<String> npcDescriptionArray = rGenerator.randDescription(npc.get(0),npc.get(16));
        List listNpc = npcDescriptionArray;
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
        text.wrappingWidthProperty().bind(descriptionVBox.widthProperty());
        descriptionText.getChildren().add(text);

        descriptionVBox.getChildren().add(descriptionText);
    }

    // liefert die Beschreibung als einfache Zeilen
    public void getDescriptionFromNPC() {
        List descriptionList = stringToList(npc.get(17));

        //ArrayList<String> descriptionList = npc.get(16);

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
                "Ideal: ", "Voice: ", "Clothing: ", "Attitude: ", "Plothook: ",
                "Job: ", "Gender: ", "Age: ", "Deity: ",
                "Size: ", "Height: ", "Placeholder", "Placeholder", "Placeholder", "Placeholder", "Placeholder", "Placeholder", "Placeholder"));

        for(int i = 0; i < descriptionList.size(); i++) {
            TextFlow descriptionTextNew = new TextFlow();
            String description = descriptionTitel.get(i);
            Text text = new Text(description);
            descriptionTextNew.getChildren().add(text);
            GridPane.setConstraints(descriptionTextNew,0,i);
            descriptionGrid.getChildren().add(descriptionTextNew);

            descriptionTextNew = new TextFlow();
            description = descriptionList.get(i).toString();
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

    }

    // Füllt den NPC-Stack
    public void makeFillNPCStack() {
        List<String> listNpc = new ArrayList<>();
        String stringNpc = "";


        //Name, Klasse, Rasse, Alignment
        GridPane nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(4);
        nameClassGrid.setHgap(25);

        Label nameL      = new Label("Name: " + npc.get(15));
        Label levelL     = new Label("Level: " + npc.get(1));
        Label classL     = new Label("Klasse: " + npc.get(3));
        Label jobL       = new Label("Job: " + npc.get(2));
        Label raceL      = new Label("Rasse: " + npc.get(0));
        Label alignmentL = new Label("Alignment: " + npc.get(16));
        Label ageL       = new Label("Alter: " + npc.get(14));
        Label sexL       = new Label("Geschlecht: " + npc.get(13));
        Label deityL     = new Label("Gottheit: " + npc.get(18));

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
        //acHpSpLangGrid.getChildren().remove(4,acHpSpLangGrid.getChildren().size());
        Label armorClassL = new Label("Rüstungsklasse: ");
        GridPane.setConstraints(armorClassL,0,0);
        Label hitPointsL  = new Label("Trefferpunkte: ");
        GridPane.setConstraints(hitPointsL,0,1);
        Label speedL      = new Label("Geschwindigkeit: ");
        GridPane.setConstraints(speedL,0,2);
        Label languagesL   = new Label("Sprachen: ");
        GridPane.setConstraints(languagesL,0,4);

        //AmorClass, HitPoints
        Label aCL = new Label(String.valueOf(npc.get(8)));
        GridPane.setConstraints(aCL,1,0);
        Label hPL = new Label(String.valueOf(npc.get(6)));
        GridPane.setConstraints(hPL,1,1);
        acHpSpLangGrid.getChildren().addAll(armorClassL,hitPointsL,speedL,languagesL,aCL,hPL);

        //Geschwindigkeit
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
        GridPane featuresGrid = new GridPane();
        featuresGrid.setPadding(new Insets(5,5,5,5));
        featuresGrid.setVgap(4);
        featuresGrid.setHgap(5);
        //featuresGrid.getChildren().remove(3,featuresGrid.getChildren().size());
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

        TextFlow actionTF = new TextFlow();
        text3 = new Text("Aktionen:\n");
        text3.setStyle("-fx-font-weight: bold");
        actionTF.getChildren().addAll(text3);

        spellActionBox.getChildren().addAll(actionTF);
        spellActionBox.setPadding(new Insets(10, 10, 10, 10));

        Line line1 = new Line(0.0f,0.0f,100f,0.0f);
        lineList.add(line1);
        Line line2 = new Line(0.0f,0.0f,100f,0.0f);
        lineList.add(line2);
        Line line3 = new Line(0.0f,0.0f,100f,0.0f);
        lineList.add(line3);
        Line line4 = new Line(0.0f,0.0f,100f,0.0f);
        lineList.add(line4);
        Line line5 = new Line(0.0f,0.0f,300.0f,0.0f);
        lineList.add(line5);


        npcStack.getChildren().addAll(nameClassGrid,line1,acHpSpLangGrid,line2,statsGrid,line3, inventoryGrid, line5,skillSensesBox,line4,featuresGrid);
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

    // gibt den AbilityScore mit Modifier zurück
    public String abilityString(int index, ArrayList<String> npc) {
        String string = "";
        List listNpc = stringToList(npc.get(4));
        List listAbilityModifier = stringToList(npc.get(5));

        if(listAbilityModifier.get(index).toString().length() > 1) {
            string = listNpc.get(index) + listAbilityModifier.get(index).toString();
        } else {string = listNpc.get(index) + "+" + listAbilityModifier.get(index);}

        return string;
    }

    // Ausgabe der Behälter
    public VBox getNpcVBox(){
        return npcStack;
    }

    public VBox getDescriptionVBox(){
        return descriptionVBox;
    }

    public ArrayList<Line> getLineList() { return lineList;}
}

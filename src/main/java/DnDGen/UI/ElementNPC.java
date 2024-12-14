package DnDGen.UI;

import DnDGen.TSWP1.NpcClass;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
import java.util.Objects;

public class ElementNPC {
    VBox npcStack, descriptionVBox;
    NpcClass npc;
    ArrayList<Line> lineList;

    /**
     * Der Konstruktor
     * @param npc der erstellte NPC
     * @author Marie
     */
    ElementNPC(NpcClass npc) {
        this.npc = npc;
        npcStack = new VBox();
        descriptionVBox = new VBox();
        lineList = new ArrayList<>();
    }

    // liefert die Beschreibung als einfache Zeilen
    public void getDescriptionFromNPC() {
        List<String> descriptionList = npc.getDescription();
        GridPane descriptionGrid = new GridPane();

        //Spaltenbreite der Tabelle definieren
        ColumnConstraints descriptionCol1Constraint = new ColumnConstraints();
        descriptionCol1Constraint.setPercentWidth(25);
        descriptionCol1Constraint.setMinWidth(65);
        ColumnConstraints descriptionCol2Constraint = new ColumnConstraints();
        descriptionCol2Constraint.setPercentWidth(75);
        descriptionGrid.getColumnConstraints().addAll(descriptionCol1Constraint,descriptionCol2Constraint);

        //Plot hook nicht da Stelle nach attitude
        ArrayList<String> descriptionTitel = new ArrayList<>(Arrays.asList("Eyes: ","Hair color: ", "Hairstyle: ",
                "Skin: ", "Trait: ", "Mannerism: ", "Bond: ", "Flaw: ",
                "Ideal: ", "Voice: ", "Clothing: ", "Attitude: ",
                "Size: ", "Height: "));

        for(int i = 0; i < descriptionList.size(); i++) {
            TextFlow descriptionTextNew = new TextFlow();
            String description = descriptionTitel.get(i);
            Text text = new Text(description);
            descriptionTextNew.getChildren().add(text);
            GridPane.setConstraints(descriptionTextNew,0,i);
            descriptionGrid.getChildren().add(descriptionTextNew);

            descriptionTextNew = new TextFlow();
            description = descriptionList.get(i);
            text = new Text(description);
            descriptionTextNew.getChildren().add(text);
            GridPane.setConstraints(descriptionTextNew,1,i);
            descriptionGrid.getChildren().add(descriptionTextNew);
        }
        descriptionVBox.getChildren().add(descriptionGrid);

        // Beschreibung als fließtext
        getDescriptionOld();
    }

    /**
     * Erstellt den Block mit Name, Klasse, Rasse, Alignment
     * @return gibt den Block als GridPane zurück
     * @author Marie
     */
    private GridPane nameClassBlock() {
        GridPane nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(4);
        nameClassGrid.setHgap(25);

        Label nameL      = new Label("Name: " + npc.getName());
        Label levelL     = new Label("Level: " + npc.getLevel());
        Label classL     = new Label("Class: " + npc.getGenClass());
        Label jobL       = new Label("Job: " + npc.getJob());
        Label raceL      = new Label("Race: " + npc.getRace());
        Label alignmentL = new Label("Alignment: " + npc.getAlignment());
        Label ageL       = new Label("Age: " + npc.getAge());

        Label sexL       = new Label("Sex: " + npc.getGender());

        Label deityL     = new Label("Deity: " + npc.getDeity());

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

        return nameClassGrid;
    }

    /**
     * Erstellt den Block mit ArmorClass, Hit points, Speed, Languages
     * @return gibt den Block als GridPane zurück
     * @author Marie
     */
    private GridPane armorClassHitPointsBlock() {

        GridPane acHpSpLangGrid = new GridPane();
        acHpSpLangGrid.setPadding(new Insets(5, 5, 5, 5));
        acHpSpLangGrid.setVgap(4);
        acHpSpLangGrid.setHgap(5);

        Label armorClassL = new Label("Armor Class: ");
        GridPane.setConstraints(armorClassL,0,0);

        Label hitPointsL  = new Label("Hit Points: ");
        GridPane.setConstraints(hitPointsL,0,1);
        Label speedL      = new Label("Movement Speed pls: ");

        GridPane.setConstraints(speedL,0,2);
        Label languagesL   = new Label("Languages: ");
        GridPane.setConstraints(languagesL,0,4);

        //AmorClass, HitPoints
        Label aCL = new Label(String.valueOf(npc.getArmorClass()));
        GridPane.setConstraints(aCL,1,0);
        Label hPL = new Label(String.valueOf(npc.getHealthPoints()));
        GridPane.setConstraints(hPL,1,1);
        acHpSpLangGrid.getChildren().addAll(armorClassL,hitPointsL,speedL,languagesL,aCL,hPL);

        //Geschwindigkeit
        List<String> listNpc = npc.getMovementSpeedList();
        for(int i = 0; i < listNpc.size(); i++) {
            Label label = new Label(listNpc.get(i));
            GridPane.setConstraints(label,1,i+2);
            acHpSpLangGrid.getChildren().add(label);
        }

        //Sprachen
        listNpc.clear();
        listNpc = npc.getLanguages();
        for(int i = 0; i < listNpc.size(); i++) {
            Label label = new Label(listNpc.get(i));
            GridPane.setConstraints(label,1,i+4);
            acHpSpLangGrid.getChildren().add(label);
        }

        return acHpSpLangGrid;
    }

    /**
     * Erstellt den Block mit den Features. Den Aktionen
     * @return gibt den Block als GridPane zurück
     * @author Marie
     */
    private GridPane featuresBlock() {
        GridPane featuresGrid = new GridPane();
        featuresGrid.setPadding(new Insets(5,5,5,5));
        featuresGrid.setVgap(4);
        featuresGrid.setHgap(5);

        Label actionL = new Label("Actions: ");
        GridPane.setConstraints(actionL,0,0);
        Label bonusActionL = new Label("Bonus Actions: ");
        GridPane.setConstraints(bonusActionL,0,1);
        Label bonusTextL = new Label("Bonus Text: ");
        GridPane.setConstraints(bonusTextL,0,2);

        ColumnConstraints featureGridColCon1 = new ColumnConstraints();
        featureGridColCon1.setMinWidth(80);
        featureGridColCon1.setFillWidth(true);
        ColumnConstraints featureGridColCon2 = new ColumnConstraints();
        featuresGrid.getColumnConstraints().addAll(featureGridColCon1, featureGridColCon2);

        //Spell
        TextFlow spellTF = new TextFlow();
        Text text1 = new Text("Spells: \n");
        text1.setStyle("-fx-font-weight: bold");
        spellTF.getChildren().addAll(text1);

        // Cantrips
        if(npc.getSpellCantrips() != null) spellTF.getChildren().add(new Text("Cantrips: \n" + getPartLevelString(npc.getSpellCantrips()) + "\n"));

        // 1
        if(npc.getFirstLevelSpell() != null) spellTF.getChildren().add(new Text("1st: \n" + getPartLevelString(npc.getFirstLevelSpell() + "\n")));

        // 2
        if(npc.getSecondLevelSpell() != null) spellTF.getChildren().add(new Text("2st: \n" + getPartLevelString(npc.getSecondLevelSpell() + "\n")));

        // 3
        if(npc.getThirdLevelSpell() != null) spellTF.getChildren().add(new Text("3st: \n" + getPartLevelString(npc.getThirdLevelSpell() + "\n")));

        // 4
        if(npc.getFourthLevelSpell() != null) spellTF.getChildren().add(new Text("4st: \n" + getPartLevelString(npc.getFourthLevelSpell() + "\n")));

        // 5
        if(npc.getFifthLevelSpell() != null) spellTF.getChildren().add(new Text("5st: \n" + getPartLevelString(npc.getFifthLevelSpell() + "\n")));

        // 6
        if(npc.getSixthLevelSpell() != null) spellTF.getChildren().add(new Text("6st: \n" + getPartLevelString(npc.getSixthLevelSpell() + "\n")));

        // 7
        if(npc.getSeventhLevelSpell() != null) spellTF.getChildren().add(new Text("7st: \n" + getPartLevelString(npc.getSeventhLevelSpell() + "\n")));

        // 8
        if(npc.getEightLevelSpell() != null) spellTF.getChildren().add(new Text("8st: \n" + getPartLevelString(npc.getEightLevelSpell() + "\n")));

        // 9
        if(npc.getNinthLevelSpell() != null) spellTF.getChildren().add(new Text("9st: \n" + getPartLevelString(npc.getNinthLevelSpell() + "\n")));



        featuresGrid.getChildren().addAll(spellTF);

        return featuresGrid;
    }

    /**
     * Erstellt den Block mit den Stats. Mit Ability Score, Saving Throws
     * @return gibt den Block als GridPane zurück
     * @author Marie
     */
    private GridPane statsBlock() {
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

        List<String> listNpc = npc.getSavingThrows();
        for (String s : listNpc) {
            String ability = s.substring(0, 3);
            String score = s.substring(4);
            for (int i = 1; i <= abilityString.size(); i++) {
                if (abilityString.get(i - 1).equals(ability)) {
                    Label label = new Label(score);
                    GridPane.setConstraints(label, i, 2);
                    statsGrid.getChildren().add(label);
                }
            }
        }
        return statsGrid;
    }

    /**
     * Erstellt den Block mit dem Inventar. Mit Rüstung, Waffen
     * @return gibt den Block als GridPane zurück
     * @author Marie
     */
    private GridPane inventoryBlock() {
        GridPane inventoryGrid = new GridPane();
        inventoryGrid.setPadding(new Insets(5, 10, 10, 10));
        inventoryGrid.setVgap(1);
        inventoryGrid.setHgap(25);

        Label armorTextL      = new Label("Armor: ");

        Label weaponTextL     = new Label("Weapons: ");
        Label armorL      = new Label("Armor: ");
        Label weaponL     = new Label("Weapons: ");

        GridPane.setConstraints(armorTextL,0,0);
        GridPane.setConstraints(weaponTextL,1,0);
        GridPane.setConstraints(armorL,0,1);
        GridPane.setConstraints(weaponL,1,1);

        inventoryGrid.getChildren().addAll(armorL, weaponL, armorTextL, weaponTextL);

        String stringNpc = "";
        List<String> listNpc = npc.getWeaponList();
        for (String s : listNpc) {
            stringNpc = stringNpc.concat("    - " + s + "\n");
        }
        weaponL.setText(stringNpc);
        weaponL.setWrapText(true);
        armorL.setText("    - " + npc.getArmor());
        armorL.setWrapText(true);

        return inventoryGrid;
    }

    /**
     * Erstellt den Block mit den skill und spells
     * @return gibt den Block als HBox zurück
     * @author Marie
     */
    private HBox skillsSpellsBlock() {
        HBox skillSensesBox = new HBox();
        skillSensesBox.setSpacing(15);
        TextFlow skillTF = new TextFlow();
        Text text1=new Text("Skills:\n");
        text1.setStyle("-fx-font-weight: bold");
        skillTF.getChildren().addAll(text1);
        if(Objects.equals(npc.getSkillList().getFirst(), "none")) {
            Text skill = new Text(npc.getSkillList().toString());
            skillTF.getChildren().add(skill);
        } else {
            for(String skill : npc.getSkillList()) {
                Text text = new Text(skill + "\n");
                skillTF.getChildren().add(text);
            }
        }

        VBox senseTF = new VBox();
        Text text3=new Text("Sense:\n");
        text3.setStyle("-fx-font-weight: bold");
        senseTF.getChildren().addAll(text3);

        List<?> senses = stringToList(npc.getSenses());
        for (Object sens : senses) {
            Text sense = new Text( sens + "\n");
            senseTF.getChildren().add(sense);
        }


        skillSensesBox.getChildren().addAll(skillTF,senseTF);

        skillSensesBox.setPadding(new Insets(10, 10, 10, 10));

        return skillSensesBox;
    }

    /**
     * Die Funktion erstellt und füllt die Ausgabe des NPCs nach der generation
     * @author Marie
     */
    public void makeFillNPCStack() {
        //Trennlinien
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

        npcStack.getChildren().addAll(nameClassBlock(),line1, armorClassHitPointsBlock(),line2,statsBlock(),line3,
                inventoryBlock(), line5,skillsSpellsBlock(),line4, featuresBlock());
    }

    /**
     * Für die spells, gibt für jedes spell level die einzelnen spells untereinander und eingerückt wieder
     * @param spells der String aller spells des levels
     * @return ein String mit den spells getrennt, untereinander und eingerückt
     * @author Marie
     */
    private String getPartLevelString(String spells) {
        String part = "";
        List<?> spell = stringToList(spells);

        for (Object o : spell) {
           part = part.concat("    - " + o + "\n");
        }
        return part;
    }

    // Ausgabe der Behälter
    public VBox getNpcVBox(){
        return npcStack;
    }

    public VBox getDescriptionVBox(){
        return descriptionVBox;
    }

    public ArrayList<Line> getLineList() { return lineList;}

    /* ----------------------
     *                      -
     * Veraltete Funktionen -
     *                      -
     * ----------------------
     */
    // Gibt das richtige Pronoun(his, her) zum sex wieder
    public String getPronoun() {
        if(npc.getGender().equals("male")) {
            return "his";
        } else {
            return "her";
        }
    }

    // Gibt das richtige possessiv Pronoun(he, she) zum sex wieder
    public String getPossessivePronoun() {
        if(npc.getGender().equals("male")) {
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
    public String abilityString(int index, NpcClass npc) {
        String string;
        List<Integer> listNpc = npc.getAbilityScores();
        List<Integer> listAbilityModifier = npc.getAbilityModifier();

        if(listAbilityModifier.get(index).toString().length() > 1) {
            string = listNpc.get(index) + listAbilityModifier.get(index).toString();
        } else {string = listNpc.get(index) + "+" + listAbilityModifier.get(index);}

        return string;
    }

    // gibt Beschreibung als Fließtext
    public void getDescriptionOld() {
        String name = npc.getName();
        List<String> listNpc = npc.getDescription();
        String feet = listNpc.get(13).substring(0,listNpc.get(13).indexOf("."));
        String inch = listNpc.get(13).substring(listNpc.get(13).indexOf(".")+1);

        TextFlow descriptionText = new TextFlow();
        String description = "\n" +name + "'s eyes are " + listNpc.getFirst() + ". " +
                             firstLetterBig(getPronoun()) + " hair is " + listNpc.get(1) + " in the Style of " + listNpc.get(2) + ". " +
                             name + "'s skin appear to be " + listNpc.get(3) + ". " +
                             firstLetterBig(getPossessivePronoun()) + " behaves " + listNpc.get(4) + ". " +
                             firstLetterBig(listNpc.get(5)) + " is " + getPronoun() + " mannerism. " +
                             firstLetterBig(getPossessivePronoun()) + " has a bond, it is that: \"" + listNpc.get(6) + "\". " +
                             name + "'s flaw is \"" + listNpc.get(7) + "\", " + getPronoun() + " ideal is " + listNpc.get(8) + ". When " +
                             getPossessivePronoun() + " speaks, " + getPronoun() + " voice sounds like " + listNpc.get(9) + ". On " +
                             getPronoun() + " body " + getPossessivePronoun() + " wears " + listNpc.get(10) + ". " +
                             firstLetterBig(getPronoun()) + " attitude is " + listNpc.get(11) + ". " +
                             name + "'s size is " + listNpc.get(12) + " with a exact height of " + feet + "'" + inch + "\".";


        Text text = new Text(description);
        text.wrappingWidthProperty().bind(descriptionVBox.widthProperty());
        descriptionText.getChildren().add(text);

        descriptionVBox.getChildren().add(descriptionText);
    }

    /**
     * Teilt einen String der die Form eines Arrays hat in einzelne Elemente und gibt sie als Liste zurück
     * @author Marie
     * @param string ein String der als Liste geschrieben werden soll.
     * @return Eine Liste mit dem Inhalt von dem string eingabe.
     */
    public List<?> stringToList(String string) {
        List<String> list = new ArrayList<>();
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
}

package DnDGen.UI;

import DnDGen.TSWP1.RandomGenerator;
import DnDGen.TSWP1.RandomNPC;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementNPC {
    VBox npcVBox, descriptionVBox;
    ArrayList<String> npc;
    RandomNPC rNPC = new RandomNPC();
    RandomGenerator rGenerator = new RandomGenerator();

    // Konstruktor
    ElementNPC(ArrayList<String> npc){
        this.npc = npc;
        npcVBox = new VBox();
        descriptionVBox = new VBox();
    }

    // liefert die Beschreibung als einfache Zeilen
    public void getDescription() {
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

    // Ausgabe der Behälter
    public VBox getNpcVBox(){
        return npcVBox;
    }

    public VBox getDescriptionVBox(){
        return descriptionVBox;
    }
}

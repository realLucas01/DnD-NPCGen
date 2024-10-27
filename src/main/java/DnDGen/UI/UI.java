package DnDGen.UI;


import DnDGen.TSWP1.NPCClass;
import DnDGen.TSWP1.RandomGenerator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
    GridPane layoutGenerateGrid;
    RandomNPC rNPC;
    ToggleGroup groupSex;
    HBox gridNpc;
    ScrollPane npcScrollPane;

	/* @author Lucas
	 *
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer
	 */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);

    public static void launchUI(String[] args) {
        launch(args);
    }

    /**
     * Funktion die als erste ausgeführt wird. In ihr werden alle anderen Methoden aufgerufen.
     * @param primaryStage das Fenster das verwendet wird.
     * @throws Exception kann exceptions werfen
     * @author Marie
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	window = primaryStage;
        window.setTitle("DnD NPC Generator");
        window.getIcons().add(new Image("ui-image/window_Icon_selfish.png"));
        rNPC = new RandomNPC();

        ElementHistory elementHistory = new ElementHistory(window, dbms);

        layoutGenerateGrid = new GridPane();
        layoutGenerateGrid.setPadding(new Insets(10, 10, 10, 10));
        layoutGenerateGrid.setVgap(7);
        layoutGenerateGrid.setHgap(10);
        layoutGenerateGrid.setMinWidth(300);


        //Spaltenbreite der Tabelle definieren
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(33);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(33);
        ColumnConstraints col3Constraints = new ColumnConstraints();
        col3Constraints.setPercentWidth(33);
        layoutGenerateGrid.getColumnConstraints().addAll(col1Constraints, col2Constraints, col3Constraints);

        npcScrollPane = new ScrollPane();
        npcScrollPane.setMinWidth(350);
        npcScrollPane.setVisible(false);

        gridNpc = new HBox();

        //Layout der Seite
        nameRasseBlock();
        klasseJobLevelBlock();
        levelAbilityBlock();
        sexBlock();

        layoutGenerateGrid.getChildren().add(makeGenerierenButton());
        VBox layoutGenerateVBox = new VBox(20);
        gridNpc.getChildren().addAll(layoutGenerateGrid, npcScrollPane);
        layoutGenerateVBox.getChildren().addAll(makeMenuBar(elementHistory),gridNpc);

        // Erstellt die Scene der generieren Seite
        generatePage = new Scene(layoutGenerateVBox, 1000, 600);
        generatePage.getStylesheets().add("/styles.css");

        // Setzte die Scene und zeigt das Fenster
        window.setScene(generatePage);
        window.show();

        // erstellt die MenuBar auf der History Seite
        elementHistory.menuBar(generatePage);

        // Wenn die Fensterbreite geändert wird passiert das hier
        window.widthProperty().addListener((obs, oldVal, newVal) -> {
            layoutGenerateGrid.setMinWidth(generatePage.getWidth()/3);
            npcScrollPane.setMinWidth(generatePage.getWidth()/3);
            npcScrollPane.setFitToWidth(true);
        });

    }

    /**
     * Erzeugt eine MenuBar für die Seite auf der, der NPC generiert wird
     * @param elementHistory Ist vom Typ ElementHistory, ist benötigt für die Weiterleitung auf diese Seite
     * @return gibt das Objekt der MenuBar zurück
     * @author Marie
     */
    private MenuBar makeMenuBar(ElementHistory elementHistory) {
        // Die MenuBar selber
        MenuBar menuBar = new MenuBar();

        // Ein Element, ein Menü in der MenuBar
        Menu menu = new Menu("Navigation");

        // Items die unter dem Element/Menü erscheinen
        MenuItem menuItem1 = new MenuItem("Item 1");
        MenuItem menuItem2 = new MenuItem("Item 2");
        MenuItem menuItemHistory = new MenuItem("History");

        // Funktionen die passieren, wenn das Item gedrückt wird
        menuItem1.setOnAction(Event::consume);

        menuItem2.setOnAction(Event::consume);

        menuItemHistory.setOnAction(e -> {
            e.consume();
            window.setScene(elementHistory.getScene());
        });

        // Items dem Element/Menü zuweisen
        menu.getItems().add(menuItem1);
        menu.getItems().add(menuItem2);
        menu.getItems().add(menuItemHistory);

        // Das Element/Menü der MenuBar zuweisen
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    /**
     * Erzeugt die Elemente für Name, Rasse und fügt diese dem Layout hinzu
     * @author Marie
     */
    private void nameRasseBlock() {
        // Name
        Label labelName = new Label("Name:");
        GridPane.setConstraints(labelName, 0, 0);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        GridPane.setConstraints(nameField,1,0,2,1);

        // Rasse
        Label labelRace = new Label("Rasse:");
        GridPane.setConstraints(labelRace, 0, 1);

        final ComboBox<String> cbRace = new ComboBox<>(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbRace, 1, 1,2,1);
        ArrayList<String> list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        cbRace.setMaxWidth(300);
        cbRace.getSelectionModel().selectFirst();

        layoutGenerateGrid.getChildren().addAll(labelName,nameField,labelRace,cbRace);
    }

    /**
     * Erzeugt die Elemente für Klasse, Job und fügt diese dem Layout hinzu
     * @author Marie
     */
    private void klasseJobLevelBlock() {
        //Klasse
        Label labelClass = new Label("Klasse:");
        GridPane.setConstraints(labelClass, 0, 2);

        final ComboBox<String> cbClass = new ComboBox<>(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbClass, 1, 2,2,1);
        ArrayList<String> list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        cbClass.setMaxWidth(300);
        cbClass.getSelectionModel().selectFirst();

        //Job
        Label labelJob = new Label("Beruf:");
        GridPane.setConstraints(labelJob, 0, 3);

        final ComboBox<String> cbJob = new ComboBox<>(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbJob, 1, 3,2,1);
        list.clear();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        cbJob.setMaxWidth(300);
        cbJob.getSelectionModel().selectFirst();

        layoutGenerateGrid.getChildren().addAll(labelClass,cbClass,labelJob,cbJob);
    }

    /**
     * Erzeugt die Elemente für Level, Ability und fügt diesem dem Layout hinzu
     * @author Marie
     */
    private void levelAbilityBlock() {
        //Level
        Label labelLevel = new Label("Level:");
        GridPane.setConstraints(labelLevel, 0, 4);

        final ComboBox<String> cbLevel = new ComboBox<>(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbLevel, 1, 4,2,1);
        for(int i = 1; i <= 20; i++) {cbLevel.getItems().add(String.valueOf(i));}
        cbLevel.setMaxWidth(300);
        cbLevel.getSelectionModel().selectFirst();

        //AbilityScore
        Label labelAbSc = new Label("Ability Score:");
        GridPane.setConstraints(labelAbSc, 0, 5);

        final ComboBox<String> cbAbSc = new ComboBox<>(FXCollections.observableArrayList("zufall"));
        GridPane.setConstraints(cbAbSc, 1, 5,2,1);
        cbAbSc.getItems().addAll(dbms.onlyColumn("abilisc"));
        cbAbSc.setMaxWidth(300);
        cbAbSc.getSelectionModel().selectFirst();

        layoutGenerateGrid.getChildren().addAll(labelLevel,cbLevel,labelAbSc,cbAbSc);
    }

    /**
     * Erzeugt den RadioButton für die Geschlechtswahl und fügt diesem dem Layout hinzu
     * @author Marie
     */
    private void sexBlock() {
        groupSex = new ToggleGroup();
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

        layoutGenerateGrid.getChildren().addAll(rbFemale,rbMale,rbRandom);
    }

    /**
     * Erstellt ein Button-Objekt für das Generieren des NPCs
     * @return gibt den konfigurieren Button zurück
     * @author Marie
     */
    private Button makeGenerierenButton() {
        Button button = new Button("Generieren");
        GridPane.setConstraints(button, 0, 13,2,3);
        button.setStyle("-fx-font: bold italic 20pt \"Arial\"; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 );");

        button.setOnAction(new ButtonGenerieren());

        return button;
    }

    /**
     * Separate Event-Handler-Klasse für den Button für das Generieren
     * @author Marie
     */
    private class ButtonGenerieren implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String name = zufallString(((TextField) layoutGenerateGrid.getChildren().get(1)).getText());
            String race = zufall((ComboBox<?>) layoutGenerateGrid.getChildren().get(3));
            String klasse = zufall((ComboBox<?>) layoutGenerateGrid.getChildren().get(5));
            String job = zufall((ComboBox<?>) layoutGenerateGrid.getChildren().get(7));
            Integer level = zufallLevel((ComboBox<?>) layoutGenerateGrid.getChildren().get(9));
            String ability = zufall((ComboBox<?>) layoutGenerateGrid.getChildren().get(11));
            String sex = zufallString(groupSex.getSelectedToggle().getUserData().toString());

            NPCClass npc = rNPC.createRandomNPC(name,race,klasse,job,level,ability,sex);

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

            // Trennlinien auf breite anpassen
            for(Line line: elementNPC.getLineList()) {
                line.setEndX(npcScrollPane.getWidth()-20);
            }

            // Den generierten Inhalt sichtbar machen
            descriptionVBox.setVisible(true);
            npcStack.setVisible(true);
            npcScrollPane.setVisible(true);
            gridNpc.getChildren().add(descriptionVBox);
        }
    }

    /**
     * Überprüft eine ComboBox, ob das Item "zufall" ausgewählt ist, wenn, dann wird null zurückgegeben.
     * @param cb die ComboBox die geprüft wird.
     * @return Beim Item "zufall" kommt null zurück, sonst das Item.
     * @author Marie
     */
    public String zufall(ComboBox<?> cb) {
        if(cb.getValue() == "zufall") {
            return null;
        } else {
            return cb.getValue().toString();
        }
    }

    /**
     * (Speziell für Integer) überprüft eine ComboBox, ob das Item "zufall" ausgewählt ist, wenn, dann wird null zurückgegeben.
     * @param cb die ComboBox die geprüft wird.
     * @return Beim Item "zufall" kommt null zurück, sonst das Item.
     * @author Marie
     */
    public Integer zufallLevel(ComboBox<?> cb) {
        if(cb.getValue() == "zufall") {
            return null;
        } else {
            return Integer.parseInt(cb.getValue().toString());
        }
    }

    /**
     * Überprüft, ob ein String leer ist, wenn, dann wird null zurückgegeben
     * @param string Der String der überprüft wird
     * @return gibt null zurück, wenn der String leer ist. Sonst den String
     * @author Marie
     */
    public String zufallString(String string) {
        if((string.equals("zufall")) || (string.isEmpty())) {
            return null;
        }
        else return string;
    }


    //---------------
    //- Alter Stuff -
    //---------------

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
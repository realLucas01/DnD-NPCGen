package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NPCClass;
import DnDGen.TSWP1.UiHistoryEditUpdate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
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

public class ElementEdit {
    private Stage window;
    private Scene scene;
    //private ArrayList<Object> npc;
    private VBox npcDate;
    private MenuBar menuBar;
    private DBMS dbms;
    private GridPane nameClassGrid;
    private UiHistoryEditUpdate editUpdate = new UiHistoryEditUpdate();
    private NPCClass cNPC;

    ElementEdit(Stage window, ArrayList<Object> npc, DBMS dbms) {
        this.window = window;
        npcDate = new VBox();
        this.dbms = dbms;
        this.cNPC = new NPCClass();
        this.cNPC.historyEntryToNPC((Integer) npc.getFirst());

        makeEditLayout();
        setDatenNPC(cNPC);

        scene = new Scene(npcDate);
    }

    /**
     * Erzeugt und steuert die menuBar
     * @param generatePage Scene für die Rückführung auf die Seite zum Generieren
     * @param elementHistory Scene für die Rückführung auf die HistorySeite
     * @author Marie
     */
    public void menuBar(Scene generatePage, Scene elementHistory) {
        Menu menu = new Menu("Navigation");
        this.menuBar = new MenuBar();

        MenuItem menuItemHistory = new MenuItem("History");
        MenuItem menuItemGenerator = new MenuItem("Generator");


        menuItemHistory.setOnAction(e -> {
            e.consume();
            window.setScene(elementHistory);
            menuItemGenerator.setVisible(true);
            menuItemHistory.setVisible(false);
        });


        menu.getItems().addAll(menuItemHistory,menuItemGenerator);
        menuBar.getMenus().add(menu);


        // Zusatz für die MenuBar
        menuItemGenerator.setOnAction(e -> {
            e.consume();
            window.setScene(generatePage);
        });

        this.npcDate.getChildren().addFirst(menuBar);

    }

    /**
     * Erstellt das Layout der Elemente, aber setzte noch keinen Inhalt
     * @author Marie
     */
    private void makeEditLayout() {
        //Name, Klasse, Rasse, Alignment
        nameClassGrid = new GridPane();
        nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
        nameClassGrid.setVgap(4);
        nameClassGrid.setHgap(25);

        // Name
        Label nameL      = new Label("Name: ");
        GridPane.setConstraints(nameL,0,0);
        TextField nameF = new TextField();
        nameF.setDisable(true);
        GridPane.setConstraints(nameF,1,0);

        // Level
        Label levelL     = new Label("Level: ");
        GridPane.setConstraints(levelL,2,3);
        final ComboBox cbLevel = new ComboBox();
        GridPane.setConstraints(cbLevel, 3, 4,4,1);
        for(int i = 1; i <= 20; i++) {cbLevel.getItems().add(i);}
        GridPane.setConstraints(cbLevel,3,3);
        levelL.setVisible(false);
        cbLevel.setVisible(false);

        // Klasse
        Label classL     = new Label("Klasse: ");
        GridPane.setConstraints(classL,0,1);
        final ComboBox cbClass = new ComboBox<>();
        ArrayList<String> list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        GridPane.setConstraints(cbClass,1,1);

        // Rasse
        Label raceL      = new Label("Rasse: ");
        GridPane.setConstraints(raceL,2,1);
        final ComboBox cbRace = new ComboBox<>();
        list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        GridPane.setConstraints(cbRace,3,1);

        // Job
        Label jobL       = new Label("Job: ");
        GridPane.setConstraints(jobL,0,2);
        final ComboBox cbJob = new ComboBox<>();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        GridPane.setConstraints(cbJob,1,2);
        jobL.setVisible(false);
        cbJob.setVisible(false);

        // Deity
        Label deityL     = new Label("Gottheit: ");
        GridPane.setConstraints(deityL,0,3);
        final ComboBox cbDeity = new ComboBox<>();
        list = dbms.onlyColumn("deity");
        list.sort(String::compareTo);
        cbDeity.getItems().addAll(list);
        GridPane.setConstraints(cbDeity,1,3);
        deityL.setVisible(false);
        cbDeity.setVisible(false);

        // Alignment
        Label alignmentL = new Label("Alignment: ");
        GridPane.setConstraints(alignmentL,2,0);
        final ComboBox cbAligmentL = new ComboBox();
        list = dbms.onlyColumn("alignment");
        list.sort(String::compareTo);
        cbAligmentL.getItems().addAll(list);
        GridPane.setConstraints(cbAligmentL,3,0);

        // Sex
        Label sexL       = new Label("Geschlecht: ");
        GridPane.setConstraints(sexL,0,4);
        final ComboBox cbGender = new ComboBox<>(FXCollections.observableArrayList("female","male"));
        GridPane.setConstraints(cbGender,1,4);
        sexL.setVisible(false);
        cbGender.setVisible(false);

        // Age
        Label ageL       = new Label("Alter: ");
        GridPane.setConstraints(ageL,2,4);
        TextField ageF = new TextField();
        GridPane.setConstraints(ageF,3,4);
        ageL.setVisible(false);
        ageF.setVisible(false);

        // AmorClass
        Label armorClassL = new Label("Rüstungsklasse: ");
        GridPane.setConstraints(armorClassL,0,5);
        TextField amorClassF = new TextField();
        GridPane.setConstraints(amorClassF,1,5);
        armorClassL.setVisible(false);
        amorClassF.setVisible(false);

        // HitPoints
        Label hitPointsL  = new Label("Trefferpunkte: ");
        GridPane.setConstraints(hitPointsL,2,5);
        TextField hitPointsF = new TextField();
        GridPane.setConstraints(hitPointsF,3,5);
        hitPointsL.setVisible(false);
        hitPointsF.setVisible(false);

        // Elemente dem Layout hinzufügen, zum leichtem Zählen in 5er-Zeilen
        nameClassGrid.getChildren().addAll(cbRace, cbClass, cbAligmentL, nameL, classL,
                                           jobL, raceL, alignmentL, ageL, sexL,
                                           deityL, levelL, armorClassL, hitPointsL, nameF,
                                           cbJob, ageF, cbGender, cbDeity, cbLevel,
                                           amorClassF, hitPointsF, classRaceEditButton());


        //Armor class, Hit points, Speed, Language, Feature
        GridPane acHpSpLangGrid = new GridPane();
        acHpSpLangGrid.setPadding(new Insets(5, 5, 5, 5));
        acHpSpLangGrid.setVgap(4);
        acHpSpLangGrid.setHgap(5);
        //acHpSpLangGrid.getChildren().remove(4,acHpSpLangGrid.getChildren().size());


        Label speedL      = new Label("Geschwindigkeit: ");
        GridPane.setConstraints(speedL,0,2);
        speedL.setVisible(false);

        // Sprachen
        Label languagesL   = new Label("Sprachen: ");
        GridPane.setConstraints(languagesL,0,4);
        final ComboBox cbLanguages = new ComboBox();
        list = dbms.onlyColumn("language");
        list.sort(String::compareTo);
        cbLanguages.getItems().addAll(list);
        languagesL.setVisible(false);
        cbLanguages.setVisible(false);
        /*
        cbLanguages.getSelectionModel().select(list.indexOf(npc.get(10)));
        GridPane.setConstraints(cbLanguages,1,4);

        */





        //AmorClass, HitPoints
        Label aCL = new Label(String.valueOf(cNPC.getArmorClass()));
        GridPane.setConstraints(aCL,1,0);
        Label hPL = new Label(String.valueOf(cNPC.getHealthPoints()));
        GridPane.setConstraints(hPL,1,1);
        aCL.setVisible(false);
        hPL.setVisible(false);
        acHpSpLangGrid.getChildren().addAll(languagesL, cbLanguages,speedL,aCL,hPL);




        npcDate.getChildren().addAll(nameClassGrid,acHpSpLangGrid);



    }

    /**
     * Implementiert den Button für die erste Phase der NPC Änderung
     * @return gibt das Button-Objekt zurück
     * @author Marie
     */
    private Button classRaceEditButton() {
        Button cRButton = new Button("weiter");
        GridPane.setConstraints(cRButton,0,2,4,2);

        cRButton.addEventHandler(ActionEvent.ACTION, e -> {
            // Hier kommt der Code hin, der beim Klicken auf den Button ausgeführt werden soll.
            List<String> changes = new ArrayList<>(3);

            changes.add((String) ((ComboBox) nameClassGrid.getChildren().get(0)).getValue());
            changes.add ((String) ((ComboBox) nameClassGrid.getChildren().get(1)).getValue());
            changes.add((String) ((ComboBox) nameClassGrid.getChildren().get(2)).getValue());

            NPCClass updateNPC = editUpdate.updateHistoryEdit(changes,cNPC);

            setDatenNPC(updateNPC);
            VisibleElements();
        });

        return cRButton;
    }

    /**
     * Füllt die Elemente mit den Daten vom NPC
     * @param npc Ist der NPC dessen Daten angezeigt werden sollen
     * @author Marie
     */
    private void setDatenNPC(NPCClass npc) {
        // Name
        ((TextField) nameClassGrid.getChildren().get(14)).setText(npc.getName());

        // Level
        ComboBox cbLevel = (ComboBox) nameClassGrid.getChildren().get(19);
        cbLevel.getSelectionModel().select(cbLevel.getItems().indexOf(npc.getLevel()));

        // Rasse
        ComboBox cbRace = (ComboBox) nameClassGrid.getChildren().get(0);
        cbRace.getSelectionModel().select(cbRace.getItems().indexOf(npc.getRace()));

        // Class
        ComboBox cbClass = (ComboBox) nameClassGrid.getChildren().get(1);
        cbClass.getSelectionModel().select(cbClass.getItems().indexOf(npc.getGenClass()));

        // Job
        ComboBox cbJob = (ComboBox) nameClassGrid.getChildren().get(15);
        cbJob.getSelectionModel().select(cbJob.getItems().indexOf(npc.getJob()));

        // Deity
        ComboBox cbDeity = (ComboBox) nameClassGrid.getChildren().get(18);
        cbDeity.getSelectionModel().select(cbDeity.getItems().indexOf(npc.getDeity()));

        // Alignment
        ComboBox cbAlignment = (ComboBox) nameClassGrid.getChildren().get(2);
        cbAlignment.getSelectionModel().select(cbAlignment.getItems().indexOf(npc.getAlignment()));

        // Sex
        ComboBox cbSex = (ComboBox) nameClassGrid.getChildren().get(17);
        cbSex.getSelectionModel().select(cbSex.getItems().indexOf(npc.getGender()));

        // Age
        ((TextField) nameClassGrid.getChildren().get(16)).setText(String.valueOf(npc.getAge()));

        // ArmorClass
        ((TextField) nameClassGrid.getChildren().get(20)).setText(String.valueOf(npc.getArmorClass()));

        // HitPoints
        ((TextField) nameClassGrid.getChildren().get(21)).setText(String.valueOf(npc.getHealthPoints()));
    }

    /**
     * Macht die Elemente sichtbar
     * @author Marie
     */
    private void VisibleElements() {
        boolean show = !(nameClassGrid.getChildren().get(21).isVisible());
        nameClassGrid.getChildren().get(5).setVisible(show);
        nameClassGrid.getChildren().get(8).setVisible(show);
        nameClassGrid.getChildren().get(9).setVisible(show);
        nameClassGrid.getChildren().get(10).setVisible(show);
        nameClassGrid.getChildren().get(11).setVisible(show);
        nameClassGrid.getChildren().get(12).setVisible(show);
        nameClassGrid.getChildren().get(13).setVisible(show);
        nameClassGrid.getChildren().get(15).setVisible(show);
        nameClassGrid.getChildren().get(16).setVisible(show);
        nameClassGrid.getChildren().get(17).setVisible(show);
        nameClassGrid.getChildren().get(18).setVisible(show);
        nameClassGrid.getChildren().get(19).setVisible(show);
        nameClassGrid.getChildren().get(21).setVisible(show);
        nameClassGrid.getChildren().get(22).setVisible(!show);
    }


    // gibt die Scene zurück
    public Scene getScene() { return scene;}
}

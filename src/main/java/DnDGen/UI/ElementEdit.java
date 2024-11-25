package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import DnDGen.TSWP1.UiHistoryEditUpdate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElementEdit {
    private final Stage window;
    private final Scene scene;
    private final VBox npcDate;
    private final DBMS dbms;
    private GridPane nameClassGrid;
    private final UiHistoryEditUpdate editUpdate = new UiHistoryEditUpdate();
    private final NpcClass cNPC;

    // Liste, um die ausgewählten Kacheln zu speichern
    private List<String> selectedTiles = new ArrayList<>();

    ElementEdit(Stage window, NpcClass npc, Integer id, DBMS dbms) {
        this.window = window;
        npcDate = new VBox();
        this.dbms = dbms;
        //this.cNPC = npc;
        this.cNPC = new NpcClass();
        cNPC.historyEntryToNpc(dbms.getHistoryEntry(id));

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
        MenuBar menuBar = new MenuBar();

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
        final ComboBox<Integer> cbLevel = new ComboBox<>();
        GridPane.setConstraints(cbLevel, 3, 4,4,1);
        for(int i = 0; i <= 20; i++) {cbLevel.getItems().add(i);}
        GridPane.setConstraints(cbLevel,3,3);
        levelL.setVisible(false);
        cbLevel.setVisible(false);

        // Klasse
        Label classL     = new Label("Class: ");
        GridPane.setConstraints(classL,0,1);
        final ComboBox<String> cbClass = new ComboBox<>();
        ArrayList<String> list = dbms.onlyColumn("class");
        Collections.sort(list);
        cbClass.getItems().addAll(list);
        GridPane.setConstraints(cbClass,1,1);

        // Rasse
        Label raceL      = new Label("Race: ");
        GridPane.setConstraints(raceL,2,1);
        final ComboBox<String> cbRace = new ComboBox<>();
        list = dbms.onlyColumn("race");
        Collections.sort(list);
        cbRace.getItems().addAll(list);
        GridPane.setConstraints(cbRace,3,1);

        // Job
        Label jobL       = new Label("Job: ");
        GridPane.setConstraints(jobL,0,2);
        final ComboBox<String> cbJob = new ComboBox<>();
        list = dbms.onlyColumn("job");
        Collections.sort(list);
        cbJob.getItems().addAll(list);
        GridPane.setConstraints(cbJob,1,2);
        jobL.setVisible(false);
        cbJob.setVisible(false);

        // Deity
        Label deityL     = new Label("Deity: ");
        GridPane.setConstraints(deityL,0,3);
        final ComboBox<String> cbDeity = new ComboBox<>();
        list = dbms.getColWithPref("deity", cNPC.getAlignment());
        list.sort(String::compareTo);
        cbDeity.getItems().addAll(list);
        GridPane.setConstraints(cbDeity,1,3);
        deityL.setVisible(false);
        cbDeity.setVisible(false);

        // Alignment
        Label alignmentL = new Label("Alignment: ");
        GridPane.setConstraints(alignmentL,2,0);
        final ComboBox<String> cbAlignmentL = new ComboBox<>();
        list = dbms.onlyColumn("alignment");
        list.sort(String::compareTo);
        cbAlignmentL.getItems().addAll(list);
        GridPane.setConstraints(cbAlignmentL,3,0);

        // Sex
        Label sexL       = new Label("Sex: ");
        GridPane.setConstraints(sexL,0,4);
        final ComboBox<String> cbGender = new ComboBox<>(FXCollections.observableArrayList("female","male"));
        GridPane.setConstraints(cbGender,1,4);
        sexL.setVisible(false);
        cbGender.setVisible(false);

        // Age
        Label ageL       = new Label("Age: ");
        GridPane.setConstraints(ageL,2,4);
        TextField ageF = new TextField();
        GridPane.setConstraints(ageF,3,4);
        ageL.setVisible(false);
        ageF.setVisible(false);

        // AmorClass
        Label armorClassL = new Label("Armor Class: ");
        GridPane.setConstraints(armorClassL,0,5);
        TextField amorClassF = new TextField();
        GridPane.setConstraints(amorClassF,1,5);
        armorClassL.setVisible(false);
        amorClassF.setVisible(false);

        // HitPoints
        Label hitPointsL  = new Label("Hit Points: ");
        GridPane.setConstraints(hitPointsL,2,5);
        TextField hitPointsF = new TextField();
        GridPane.setConstraints(hitPointsF,3,5);
        hitPointsL.setVisible(false);
        hitPointsF.setVisible(false);


        // Geschwindigkeit
        GridPane speedGrid = new GridPane();
        GridPane.setConstraints(speedGrid,0,6,4,6);
        speedGrid.setVgap(5);
        speedGrid.setHgap(20);

        Label speedL = new Label("Movement speed: ");
        GridPane.setConstraints(speedL,0,1);
        Label feetSpeedL = new Label("walking Speed: ");
        GridPane.setConstraints(feetSpeedL,1,0);
        Label wingSpeedL = new Label("flying Speed: ");
        GridPane.setConstraints(wingSpeedL,2,0);

        // Erstelle einen Spinner (Textfeld mit PfeilTasten) für Integer-Werte
        Spinner<Integer> feetSpeedSpinner = new Spinner<>();
        Spinner<Integer> wingSpeedSpinner = new Spinner<>();

        // Setze den Bereich des Spinners (z.B. von 0 bis 100)
        SpinnerValueFactory<Integer> valueFactoryFeet = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0); // Min, Max, Initial
        SpinnerValueFactory<Integer> valueFactoryWing = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        feetSpeedSpinner.setValueFactory(valueFactoryFeet);
        wingSpeedSpinner.setValueFactory(valueFactoryWing);

        GridPane.setConstraints(feetSpeedSpinner,1,1);
        GridPane.setConstraints(wingSpeedSpinner,2,1);


        speedGrid.getChildren().addAll(speedL,feetSpeedL,wingSpeedL,feetSpeedSpinner,wingSpeedSpinner);
        speedGrid.setVisible(false);


        // Languages
        Label languagesL = new Label("Languages: ");
        GridPane.setConstraints(languagesL,0,0);

        Label languagesFeld = new Label();
        GridPane.setConstraints(languagesFeld,1,0, 2,1);

            // Bild für den Button
        Image image = new Image("ui-image/edit.png"); // Beispielbild-URL
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

            // Button mit Bild
        Button languagesButton = new Button("Öffne PopUp", imageView);
        GridPane.setConstraints(languagesButton,3,0);

            // Button Aktion
        languagesButton.setOnAction(e -> openPopup(window));

        GridPane languagesGrid = new GridPane();
        languagesGrid.getChildren().addAll(languagesL, languagesFeld, languagesButton);
        //languagesGrid.setVisible(false);
        for(Node elm: languagesGrid.getChildren()) {
            elm.setVisible(false);
        }

        // Ability Score

        // Inventar

        // Skills

        // Senses

        // Spells




        // Elemente dem Layout hinzufügen, zum leichtem Zählen in 5er-Zeilen
        nameClassGrid.getChildren().addAll(cbRace, cbClass, cbAlignmentL, nameL, classL,
                                           jobL, raceL, alignmentL, ageL, sexL,
                                           deityL, levelL, armorClassL, hitPointsL, nameF,
                                           cbJob, ageF, cbGender, cbDeity, cbLevel,
                                           amorClassF, hitPointsF, classRaceEditButton(), speedGrid);

        npcDate.getChildren().addAll(nameClassGrid, languagesGrid);
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
            List<String> changes = new ArrayList<>(3);

            // Änderungen aus ComboBoxen Rasse, Klasse und Alignment holen und für bearbeitung deaktivieren
            for (int i = 0; i < 3; i++) {
                changes.add((String) ((ComboBox<?>) nameClassGrid.getChildren().get(i)).getValue());
                nameClassGrid.getChildren().get(i).setDisable(true);
            }

            // NPC bearbeiten
            NpcClass updateNPC = editUpdate.updateHistoryEdit(changes,cNPC);

            // Geänderte Daten anzeigen lassen
            selectedTiles = updateNPC.getLanguages();
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
    private void setDatenNPC(NpcClass npc) {
        // Name
        ((TextField) nameClassGrid.getChildren().get(14)).setText(npc.getName());

        // Level
        ComboBox<?> cbLevel = (ComboBox<?>) nameClassGrid.getChildren().get(19);
        cbLevel.getSelectionModel().select(cbLevel.getItems().indexOf(npc.getLevel()));

        // Rasse
        ComboBox<?> cbRace = (ComboBox<?>) nameClassGrid.getChildren().get(0);
        cbRace.getSelectionModel().select(cbRace.getItems().indexOf(npc.getRace()));

        // Class
        ComboBox<?> cbClass = (ComboBox<?>) nameClassGrid.getChildren().get(1);
        cbClass.getSelectionModel().select(cbClass.getItems().indexOf(npc.getGenClass()));

        // Job
        ComboBox<?> cbJob = (ComboBox<?>) nameClassGrid.getChildren().get(15);
        cbJob.getSelectionModel().select(cbJob.getItems().indexOf(npc.getJob()));

        // Deity
        ComboBox<String> cbDeity = (ComboBox<String>) nameClassGrid.getChildren().get(18);
        ArrayList<String> list = dbms.getColWithPref("deity", npc.getAlignment());
        list.sort(String::compareTo);
        cbDeity.getItems().clear();

        cbDeity.getItems().addAll(list);
            // wählt den Gott den der Npc hat aus
        //cbDeity.getSelectionModel().select(cbDeity.getItems().indexOf(npc.getDeity()));
        cbDeity.getSelectionModel().select(0);

        // Alignment
        ComboBox<?> cbAlignment = (ComboBox<?>) nameClassGrid.getChildren().get(2);
        cbAlignment.getSelectionModel().select(cbAlignment.getItems().indexOf(npc.getAlignment()));

        // Sex
        ComboBox<?> cbSex = (ComboBox<?>) nameClassGrid.getChildren().get(17);
        cbSex.getSelectionModel().select(cbSex.getItems().indexOf(npc.getGender()));

        // Age
        ((TextField) nameClassGrid.getChildren().get(16)).setText(String.valueOf(npc.getAge()));

        // ArmorClass
        ((TextField) nameClassGrid.getChildren().get(20)).setText(String.valueOf(npc.getArmorClass()));

        // HitPoints
        ((TextField) nameClassGrid.getChildren().get(21)).setText(String.valueOf(npc.getHealthPoints()));

        // Movement Speed (wenn weitere arten da sind, hier ändern)
        Integer walking = Integer.parseInt(npc.getMovementSpeedList().getFirst().substring(0,2));
        Integer flying = Integer.parseInt(npc.getMovementSpeedList().getLast().substring(0,2));
        if(npc.getMovementSpeedList().size() == 1) {
            ((Spinner<Integer>) ((GridPane) nameClassGrid.getChildren().get(23)).getChildren().get(3)).getValueFactory().setValue(walking);
        } else {
            ((Spinner<Integer>) ((GridPane) nameClassGrid.getChildren().get(23)).getChildren().get(3)).getValueFactory().setValue(walking);
            ((Spinner<Integer>) ((GridPane) nameClassGrid.getChildren().get(23)).getChildren().get(4)).getValueFactory().setValue(flying);
        }

        // Languages
            // Index des GridPane bestimmen (abhängig davon, ob MenuBar existiert) (krasse if-Bedingung)
        int gridIndex = (npcDate.getChildren().get(0) instanceof MenuBar) ? 2 : 1;

            // Zugriff auf das GridPane, das die Sprachen enthält
        GridPane languagesGrid = (GridPane) npcDate.getChildren().get(gridIndex);

            // Erstellen eines Strings, der alle ausgewählten Sprachen enthält
        StringBuilder stringBuilder = new StringBuilder();
        for (String lang : selectedTiles) {
            stringBuilder.append(lang).append("\n");
        }

            // Aktualisieren des Texts im entsprechenden Label
        ((Label) languagesGrid.getChildren().get(1)).setText(stringBuilder.toString());



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
        nameClassGrid.getChildren().get(20).setVisible(show);
        nameClassGrid.getChildren().get(21).setVisible(show);
        nameClassGrid.getChildren().get(22).setVisible(!show);
        nameClassGrid.getChildren().get(23).setVisible(show);

        //npcDate.getChildren().get(2).setVisible(show);

        GridPane grid = (GridPane) npcDate.getChildren().get(2);
        for (Node node: grid.getChildren()) {
            node.setVisible(show);
        }



    }

    /**
     * Öffnet für Languages ein Pop-up-Fenster für die auswahl der Sprachen
     * @param owner die Stage die offen ist, also die Haupt-Stage
     * @author chatGPT
     * @author Marie
     */
    private void openPopup(Stage owner) {
        // Neues Fenster erstellen
        Stage popup = new Stage();
        popup.setTitle("Popup-Fenster");
        popup.initModality(Modality.WINDOW_MODAL); // Sperrt Interaktion mit dem Hauptfenster
        popup.initOwner(owner);

        // Inhalt des Pop-up-Fensters
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px; -fx-alignment: center;");

        // Dummy-Daten für die Kacheln

        List<String> items = dbms.onlyColumn("language");

        // TilePane für die Kacheln
        TilePane tilePane = new TilePane();
        tilePane.setHgap(10); // Horizontaler Abstand zwischen Kacheln
        tilePane.setVgap(10); // Vertikaler Abstand zwischen Kacheln
        tilePane.setPrefColumns(3); // Anzahl der Spalten

        // Für jedes Element in der Liste eine Kachel hinzufügen
        for (String item : items) {
            Button tile = new Button(item);
            tile.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
            tile.setPrefSize(100, 50); // Größe der Kachel

            // Setzen der anfänglichen Kachelmarkierung basierend auf selectedTiles
            if (selectedTiles.contains(item)) {
                tile.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;"); // Als ausgewählt markieren
            } else {
                tile.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;"); // Als nicht ausgewählt setzen
            }

            // Hinzufügen des EventHandlers für die Benutzerinteraktion
            tile.setOnAction(e -> {
                if (selectedTiles.contains(item)) {
                    // Entfernen, wenn bereits ausgewählt
                    selectedTiles.remove(item);
                    tile.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
                } else {
                    // Hinzufügen, wenn nicht ausgewählt
                    selectedTiles.add(item);
                    tile.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");
                }
            });

            tilePane.getChildren().add(tile);
        }

        // Button für die Bestätigung
        Button confirmButton = new Button("Bestätigen");
        confirmButton.setOnAction(e -> {
            // Liste der ausgewählten Kacheln ausgeben oder weiterreichen
            // Index des GridPane bestimmen (abhängig davon, ob MenuBar existiert) (krasse if-Bedingung)
            int gridIndex = (npcDate.getChildren().get(0) instanceof MenuBar) ? 2 : 1;

            // Zugriff auf das GridPane, das die Sprachen enthält
            GridPane languagesGrid = (GridPane) npcDate.getChildren().get(gridIndex);

            // Erstellen eines Strings, der alle ausgewählten Sprachen enthält
            StringBuilder stringBuilder = new StringBuilder();
            for (String lang : selectedTiles) {
                stringBuilder.append(lang).append("\n");
            }

            // Aktualisieren des Texts im entsprechenden Label
            ((Label) languagesGrid.getChildren().get(1)).setText(stringBuilder.toString());
            popup.close();
        });

        // Layout zusammenstellen
        layout.getChildren().addAll(tilePane, confirmButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene popupScene = new Scene(layout, 450, 430);
        popup.setScene(popupScene);
        popup.show();
    }



    // gibt die Scene zurück
    public Scene getScene() { return scene;}
}

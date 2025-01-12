package DnDGen.UI;

import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;
import DnDGen.TSWP1.UiHistoryEditUpdate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ALL")
public class ElementEdit {
    private final Stage window;
    private final Scene scene;
    private final VBox npcDate;
    private final DBMS dbms;
    private GridPane nameClassGrid;
    private final UiHistoryEditUpdate editUpdate = new UiHistoryEditUpdate();
    private final NpcClass cNPC;
    private GridPane abilityGrid;
    private HBox inventoryBox;
    private HBox skillBox;

    // Liste, um die ausgewählten Kacheln zu speichern
    private List<String> languagesTiles = new ArrayList<>();
    private List<String> armorTiles = new ArrayList<>();
    private List<String> weaponTiles = new ArrayList<>();
    private Button classTile = new Button();

    ElementEdit(Stage window, Integer id, DBMS dbms) {
        this.window = window;
        npcDate = new VBox();
        this.dbms = dbms;
        this.cNPC = new NpcClass();
        cNPC.historyEntryToNpc(dbms.getHistoryEntry(id));

        makeEditLayout();
        setDatenNPC(cNPC);

        scene = new Scene(npcDate);
        scene.getStylesheets().add("styles.css");
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


        // Text statt Label für mehrzeiligen Text
        Text languagesFeld = new Text();
        languagesFeld.setWrappingWidth(50); // Optional: Breite setzen, damit Text umbrechen kann
        GridPane.setConstraints(languagesFeld, 1, 0, 2, 1);

            // Bild für den Button
        Image image = new Image("ui-image/edit.png"); // Beispielbild-URL
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

            // Button mit Bild
        Button languagesButton = new Button("Öffne PopUp", imageView);
        GridPane.setConstraints(languagesButton,3,0);

            // Button Aktion
        languagesButton.setOnAction(e -> openPopup(window, "language"));

        GridPane languagesGrid = new GridPane();
        languagesGrid.getChildren().addAll(languagesL, languagesFeld, languagesButton);
        //languagesGrid.setVisible(false);
        for(Node elm: languagesGrid.getChildren()) {
            elm.setVisible(false);
        }


        // Ability-scores: Optimiertes Layout
        abilityGrid = new GridPane();
        abilityGrid.setHgap(5); // Reduzierter horizontaler Abstand
        abilityGrid.setVgap(5); // Reduzierter vertikaler Abstand
        abilityGrid.setPadding(new Insets(5, 5, 5, 5)); // Weniger Padding
        abilityGrid.setAlignment(Pos.CENTER); // Zentriert das gesamte GridPane

            // Spaltenkonfiguration: Platz sparen durch relative Größen
        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.ALWAYS); // Spalten können wachsen
            colConstraints.setPercentWidth(10); // Jede Spalte nimmt ca. 33% ein
            abilityGrid.getColumnConstraints().add(colConstraints);
        }

            // Liste der Ability Scores (Str, Dex, Con, etc.)
        ArrayList<String> abilityString = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Cha"));

            // Gehe durch jede Fähigkeit und platziere die entsprechenden Felder
        for (int i = 0; i < abilityString.size(); i++) {
            // Label für die Fähigkeit
            Label label = new Label(abilityString.get(i));
            label.setStyle("-fx-font-weight: normal; -fx-font-size: 12px;"); // Kleine Schriftgröße
            GridPane.setConstraints(label, 0, i);

            // TextField für den Ability Score
            TextField fieldAb = new TextField();
            fieldAb.setPrefWidth(10); // Kleineres Textfeld
            GridPane.setConstraints(fieldAb, 1, i);

            // Plus-Symbol als Label
            Label labelPlus = new Label("+");
            labelPlus.setStyle("-fx-font-size: 12px;"); // Kleine Schriftgröße
            GridPane.setHalignment(labelPlus, HPos.CENTER); // Zentriert das Plus-Symbol
            GridPane.setConstraints(labelPlus, 2, i);

            // TextField für den Modifier
            TextField fieldMod = new TextField();
            fieldMod.setPrefWidth(40); // Kleineres Textfeld
            GridPane.setConstraints(fieldMod, 3, i);

            // Füge alle Elemente der GridPane hinzu
            abilityGrid.getChildren().addAll(label, fieldAb, labelPlus, fieldMod);
        }

        abilityGrid.setVisible(false);



        // Inventar
        inventoryBox = new HBox();
        Label inventoryL = new Label("Inventory");

          // Armor
        VBox armorBox = new VBox();
        Label armorL = new Label("Armor:");
        armorL.getStyleClass().add("bold-label");
        Text armorText = new Text();

        armorBox.getChildren().addAll(armorL,armorText);


          // weapon
        VBox weaponBox = new VBox();
        Label weaponL = new Label("Weapon:");
        weaponL.getStyleClass().add("bold-label");
        Text weaponText = new Text();

        weaponBox.getChildren().addAll(weaponL,weaponText);


          // Knöpfe für Rüstung und Waffe wählen
        VBox buttonBox = new VBox();

          // Button mit Bild
        ImageView armorImageView = new ImageView(image);
        armorImageView.setFitWidth(20);
        armorImageView.setFitHeight(20);
        Button armorButton = new Button("Choose Armor", armorImageView);
        ImageView weaponImageView = new ImageView(image);
        weaponImageView.setFitWidth(20);
        weaponImageView.setFitHeight(20);
        Button weaponButton = new Button("Choose Weapon", weaponImageView);

          // Button Aktion
        armorButton.setOnAction(e -> openPopup(window,"armor"));
        weaponButton.setOnAction(e -> openPopup(window,"weapon"));

        buttonBox.getChildren().addAll(armorButton, weaponButton);


        inventoryBox.getChildren().addAll(inventoryL, armorBox, weaponBox, buttonBox);






        // Skills
        skillBox = new HBox();
        Label skillL = new Label("Skills");
        skillL.getStyleClass().add("bold-label");
        Text skillText = new Text();

        // Button mit Bild
        ImageView skillImageView = new ImageView(image);
        armorImageView.setFitWidth(20);
        armorImageView.setFitHeight(20);
        Button skillButton = new Button("Choose Skills", skillImageView);

        // Button Aktion
        skillButton.setOnAction(e -> openPopup(window,"skill"));

        skillBox.getChildren().addAll(skillL, skillText, skillButton);















        // Senses

        // Spells




        // Elemente dem Layout hinzufügen, zum leichtem Zählen in 5er-Zeilen
        nameClassGrid.getChildren().addAll(cbRace, cbClass, cbAlignmentL, nameL, classL,
                                           jobL, raceL, alignmentL, ageL, sexL,
                                           deityL, levelL, armorClassL, hitPointsL, nameF,
                                           cbJob, ageF, cbGender, cbDeity, cbLevel,
                                           amorClassF, hitPointsF, classRaceEditButton(), speedGrid);

        npcDate.getChildren().addAll(nameClassGrid, languagesGrid, abilityGrid, inventoryBox, skillBox);
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
            languagesTiles = updateNPC.getLanguages();
            weaponTiles = updateNPC.getWeaponList();
            List<String> armorList = new ArrayList<>(dbms.onlyColumn("armor"));


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
        for (String lang : languagesTiles) {
            stringBuilder.append(lang).append("\n");
        }

            // Aktualisieren des Texts im entsprechenden Label
        ((Text) languagesGrid.getChildren().get(1)).setText(stringBuilder.toString());


        /*
        // Ability Scores
        List<Integer> abilityString = npc.getAbilityScores();
        List<Integer> abilityMod = npc.getAbilityModifier();
        int j = 0;

        for(int i = 7; i < 23; i+=3) {
            // Ability
            TextField fieldAb = (TextField) abilityGrid.getChildren().get(i-1);
            fieldAb.setText(abilityString.get(j).toString());

            // Modifierer
            TextField fieldMod = (TextField) abilityGrid.getChildren().get(i+1);
            fieldMod.setText(abilityMod.get(j).toString());

            j++;
        }
         */

        // Ability Scores aus NPC
        List<Integer> abilityScores = npc.getAbilityScores(); // Werte der Fähigkeiten
        List<Integer> abilityModifiers = npc.getAbilityModifier(); // Modifikatoren der Fähigkeiten

        // Sicherstellen, dass die Listen gleich groß sind
        if (abilityScores.size() != abilityModifiers.size()) {
            throw new IllegalStateException("Ability Scores und Modifikatoren haben unterschiedliche Größen!");
        }

        // Fülle die Felder in der GridPane
        for (int i = 0; i < abilityScores.size(); i++) {
            // Hole die TextField-Elemente aus der GridPane
            TextField fieldAb = (TextField) getNodeByRowColumnIndex(i, 1, abilityGrid); // Ability Score
            TextField fieldMod = (TextField) getNodeByRowColumnIndex(i, 3, abilityGrid); // Modifier

            // Setze die Werte in die entsprechenden Felder
            if (fieldAb != null) {
                fieldAb.setText(abilityScores.get(i).toString());
            }

            if (fieldMod != null) {
                fieldMod.setText(abilityModifiers.get(i).toString());
            }
        }


        Node inventoryNode = npcDate.getChildren().get(3);

        if (inventoryNode instanceof HBox) {
            HBox inventoryBox = (HBox) inventoryNode;
            VBox armorBox = (VBox) inventoryBox.getChildren().get(1);
            Text armorText = (Text) armorBox.getChildren().get(1);

            StringBuilder armorStringBuilder = new StringBuilder();
            for (String armor : armorTiles) {
                armorStringBuilder.append(armor).append("\n");
            }
            armorText.setText(armorStringBuilder.toString());

            VBox weaponBox = (VBox) inventoryBox.getChildren().get(2);
            Text weaponText = (Text) weaponBox.getChildren().get(1);

            StringBuilder weaponStringBuilder = new StringBuilder();
            for (String weapon : weaponTiles) {
                weaponStringBuilder.append(weapon).append("\n");
            }
            weaponText.setText(weaponStringBuilder.toString());
        } else {
            inventoryNode = npcDate.getChildren().get(4);
            HBox inventoryBox = (HBox) inventoryNode;
            VBox armorBox = (VBox) inventoryBox.getChildren().get(1);
            Text armorText = (Text) armorBox.getChildren().get(1);

            StringBuilder armorStringBuilder = new StringBuilder();
            for (String armor : armorTiles) {
                armorStringBuilder.append(armor).append("\n");
            }
            armorText.setText(armorStringBuilder.toString());

            VBox weaponBox = (VBox) inventoryBox.getChildren().get(2);
            Text weaponText = (Text) weaponBox.getChildren().get(1);

            StringBuilder weaponStringBuilder = new StringBuilder();
            for (String weapon : weaponTiles) {
                weaponStringBuilder.append(weapon).append("\n");
            }
            weaponText.setText(weaponStringBuilder.toString());
        }



    }

    // Hilfsmethode: Knoten aus GridPane anhand von Zeile und Spalte holen
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    return node;
                }
            }
        }
        return null; // Kein passender Knoten gefunden
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

        abilityGrid.setVisible(show);
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
    private void openPopup(Stage owner, String searchString) {
        Stage popup = new Stage();
        popup.setTitle(searchString);
        popup.initModality(Modality.WINDOW_MODAL);
        popup.initOwner(owner);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10px; -fx-alignment: center;");

        List<String> items = loadItems(searchString);
        List<String> selectedList = getSelectedList(searchString);

        TilePane tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPrefColumns(3);

        for (String item : items) {
            Button tile = new Button(item);
            updateTileStyle(tile, selectedList.contains(item));

            tile.setOnAction(e -> {
                if (selectedList.contains(item)) {
                    selectedList.remove(item);
                    updateTileStyle(tile, false);
                } else {
                    selectedList.add(item);
                    updateTileStyle(tile, true);
                }
            });

            tilePane.getChildren().add(tile);
        }

        Button confirmButton = new Button("OK");
        confirmButton.setOnAction(e -> {
            int gridIndex = (npcDate.getChildren().getFirst() instanceof MenuBar) ? 2 : 1;
            GridPane languagesGrid = (GridPane) npcDate.getChildren().get(gridIndex);
            updateGridPaneWithSelected(languagesGrid, selectedList, searchString);
            popup.close();
        });

        layout.getChildren().addAll(tilePane, confirmButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene popupScene = new Scene(layout, 450, 430);
        popup.setScene(popupScene);
        popup.show();
    }



    private void updateGridPaneWithSelected(GridPane gridPane, List<String> selectedItems, String searchString) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : selectedItems) {
            stringBuilder.append(item).append("\n");
        }

        // Erkennen, welches Textfeld aktualisiert werden soll
        Text targetText;
        switch (searchString) {
            case "armor":
                targetText = (Text) ((VBox) inventoryBox.getChildren().get(1)).getChildren().get(1);
                break;
            case "weapon":
                targetText = (Text) ((VBox) inventoryBox.getChildren().get(2)).getChildren().get(1);
                break;
            case "language":
            default:
                targetText = (Text) gridPane.getChildren().get(1); // Standard: Languages
                break;
        }

        targetText.setText(stringBuilder.toString());
    }


    private void updateTileStyle(Button tile, boolean isSelected) {
        if (isSelected) {
            tile.setStyle("-fx-background-color: lightblue; -fx-padding: 10px;");
        } else {
            tile.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
        }
    }


    private List<String> getSelectedList(String searchString) {
        switch (searchString) {
            case "language":
                return languagesTiles;
            case "armor":
                return armorTiles;
            case "weapon":
                return weaponTiles;
            default:
                return new ArrayList<>();
        }
    }


    // Methode zum dynamischen Laden von Daten
    private List<String> loadItems(String searchString) {
        return dbms.onlyColumn(searchString);
    }




    // gibt die Scene zurück
    public Scene getScene() { return scene;}
}

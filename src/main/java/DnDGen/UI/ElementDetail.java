package DnDGen.UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import DnDGen.DB.DBMS;
import DnDGen.TSWP1.NpcClass;

public class ElementDetail {
	private Scene scene;
	private VBox vbox;
	private MenuBar menuBar;
	private Stage window;
	private NpcClass npc;
	private GridPane nameClassGrid;
	private final VBox npcDate;

	public ElementDetail(Stage window, NpcClass npc, Integer id, DBMS dbms) {

		vbox = new VBox();		
		npcDate = new VBox();
		this.window = window;
		scene = new Scene(npcDate, 800, 600);
		window.setScene(scene);
		makeDetailLayout();

		window.show();
	}

	public Scene getScene() {
		return scene;
	}

	public void menuBar(Scene elementHistory, Scene generatePage) {
		Menu menu = new Menu("Navigation");
		this.menuBar = new MenuBar();

		MenuItem menuItemHistory = new MenuItem("History");
		MenuItem menuItemGenerator = new MenuItem("Generator");

		menu.getItems().addAll(menuItemHistory, menuItemGenerator);
		menuBar.getMenus().add(menu);

		npcDate.getChildren().addFirst(menuBar);

		menuItemHistory.setOnAction(e -> {
			e.consume();
			// layoutGenerateGrid.setVisible(false);
			window.setScene(generatePage);
		});

		menuItemGenerator.setOnAction(e -> {
			e.consume();
			window.setScene(elementHistory);
		});

	}

	public void makeDetailLayout() {

		nameClassGrid = new GridPane();
		nameClassGrid.setPadding(new Insets(5, 10, 10, 10));
		nameClassGrid.setVgap(4);
		nameClassGrid.setHgap(25);

		Label nameL = new Label("name: " + npc.getName());
		GridPane.setConstraints(nameL, 0, 0);
		TextField nameF = new TextField();
		nameF.setDisable(true);
		GridPane.setConstraints(nameF, 1, 0);

		// vbox.getChildren().addFirst(nameL);

		Label genClass = new Label("class: " + npc.getGenClass());
		vbox.getChildren().addFirst(genClass);

		Label race = new Label("race: " + npc.getRace());
		vbox.getChildren().addFirst(race);

		Label deity = new Label("deity: " + npc.getDeity());
		vbox.getChildren().addFirst(deity);

		Label age = new Label("age: " + npc.getAge());
		vbox.getChildren().addFirst(age);

		Label level = new Label("level: " + npc.getLevel());
		vbox.getChildren().addFirst(level);

		Label job = new Label("job: " + npc.getJob());
		vbox.getChildren().addFirst(job);

		Label alignment = new Label("alignment: " + npc.getAlignment());
		vbox.getChildren().addFirst(alignment);

		Label gender = new Label("gender: " + npc.getGender());
		vbox.getChildren().addFirst(gender);

		Label armorClass = new Label("armor class: " + npc.getArmorClass());
		vbox.getChildren().addFirst(armorClass);

		Label healthpoints = new Label("healthpoints: " + npc.getHealthPoints());
		vbox.getChildren().addFirst(healthpoints);

		Label speed = new Label("speed: " + npc.getMovementSpeedList());
		vbox.getChildren().addFirst(speed);

		Label languages = new Label("languages: " + npc.getLanguages());
		vbox.getChildren().addFirst(languages);

		Label armor = new Label("class: " + npc.getArmor());
		vbox.getChildren().addFirst(armor);

		nameClassGrid.getChildren().addAll(nameL, nameF);

		npcDate.getChildren().addAll(nameClassGrid);

	}

}
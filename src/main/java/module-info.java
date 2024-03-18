module com.example.demoGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports testWindow to javafx.graphics;

    opens com.example.demoGUI to javafx.fxml;
}
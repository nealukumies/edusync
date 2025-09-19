package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainLayoutController {
    @FXML
    private HBox headerContent;
    @FXML
    private VBox content;

    public void initialize() {
        changePage();
    }

    public void generateHeader() {
        // Once we have a user model, this method will determine whether to show personal header or generic
        // But for now, just use the generic "log in" header
        headerContent.getChildren().clear();

        // Dynamically forces all content to the right edge of the container
        Region spacer = new Region();
        headerContent.setHgrow(spacer, Priority.ALWAYS);

        Label loginText = new Label("Log In");
        loginText.getStyleClass().add("medium-title");
        headerContent.getChildren().addAll(spacer, loginText);
    }

    // Will handle logic for swapping out content in the "content" VBox
    public void changePage() {
        generateHeader();
    }
}

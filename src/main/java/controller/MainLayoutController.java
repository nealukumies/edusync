package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainLayoutController {
    @FXML
    private HBox headerContent;
    @FXML
    private StackPane content;

    public void initialize() throws IOException {
        changePage(Page.FRONT_PAGE);
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
        loginText.getStyleClass().add("link");
        headerContent.getChildren().addAll(spacer, loginText);
    }

    public void changePage(Page page) throws IOException {
        generateHeader();

        content.getChildren().clear();

        switch (page) {
            case FRONT_PAGE:
                loadPage("/FrontpageView.fxml");
                break;
            case DASHBOARD_PAGE:
                loadPage("/DashboardView.fxml");
                break;
        }
    }

    public void loadPage(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent root = fxmlLoader.load();
        content.getChildren().add(root);
    }
}

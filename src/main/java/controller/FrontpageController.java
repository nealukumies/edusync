package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FrontpageController extends SubController {
    @FXML
    private Button startButton;

    public void initializeFully() {
        startButton.setOnAction(event -> this.getMainController().changePage(Page.LOGIN_PAGE));
    }
}

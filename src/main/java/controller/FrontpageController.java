package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the frontpage view.
 */
public class FrontpageController extends SubController {
    /** Start button to navigate to the login page */
    @FXML
    private Button startButton;

    @Override
    public void initializeFully() {
        startButton.setOnAction(event -> this.getMainController().changePage(Page.LOGIN_PAGE));
    }
}

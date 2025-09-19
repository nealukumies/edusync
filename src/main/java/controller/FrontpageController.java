package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FrontpageController extends SubController {
    private MainController mainController;
    @FXML
    private Button startButton;

    public void setMainViewController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initializeFully() {
        startButton.setOnAction(event -> {
            mainController.changePage(Page.ADD_COURSE_PAGE);
        });
    }
}

package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddCourseController extends SubController {
    private MainController mainController;

    @FXML
    private Button submit;

    @Override
    public void setMainViewController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initializeFully() {
        submit.setOnAction(event -> {
            mainController.changePage(Page.FRONT_PAGE);
        });
    }
}

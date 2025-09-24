package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddAssignmentController extends SubController {
    @FXML
    private Button submit;

    @Override
    public void initializeFully() {
        submit.setOnAction(event -> {
            this.getMainController().changePage(Page.FRONT_PAGE);
        });
    }
}

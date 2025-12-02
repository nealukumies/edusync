package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Controller for the calendar view.
 */
public class CalendarController extends SubController {
    /** Root VBox for the calendar view */
    @FXML
    private VBox root; // fx:id="root" in FXML

    @Override
    public void initializeFully() {
        // Nothing special for now
    }
}

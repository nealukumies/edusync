package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddAssignmentController extends SubController {
    @FXML
    private Button submit;
    private int[] time = new int[2];

    @Override
    public void initializeFully() {
        submit.setOnAction(event -> {
            this.getMainController().changePage(Page.FRONT_PAGE);
        });
    }

    public void parseTimeString(String ts) {
        if (ts.contains(":")) {
            try {
                String[] chars = ts.split(":");
                int hours = Integer.parseInt(chars[0]);
                int minutes = Integer.parseInt(chars[1]);
                if (hours > 23 || hours < 0) {
                    time[0] = 0;
                }
                if (minutes > 59 || minutes < 0) {
                    time[1] = 0;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

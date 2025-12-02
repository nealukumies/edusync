package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.handlers.CourseHandler;
import view.MainView;

public class AddCourseController extends SubController {
    @FXML
    private TextField title;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button submit;
    @FXML
    private Button cancel;
    @FXML
    private Label errorMessage;

    @Override
    public void initializeFully() {
        submit.setOnAction(event -> createCourse());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());

    }

    public boolean validateTitle() {
        return !title.getText().trim().isEmpty();
    }

    public boolean validateStartDate() {
        return startDate.getValue() != null;
    }

    public boolean validateEndDate() {
        return endDate.getValue() != null;
    }

    public void createCourse() {
        if (validateTitle() && validateStartDate() && validateEndDate()) {
            String titleString = title.getText();
            String startDateString = startDate.getValue().toString();
            String endDateString = endDate.getValue().toString();
            try {
                CourseHandler.createCourse(titleString, startDateString, endDateString);
                this.getMainController().changePage(Page.COURSE_LIST_PAGE);
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        } else {
            String errormessage = MainView.getBundle().getString("course_errortext");
            errorMessage.setText(errormessage);
        }
    }
}

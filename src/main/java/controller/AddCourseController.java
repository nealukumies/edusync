package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCourseController extends SubController {
    @FXML
    private Label errorMessage;
    @FXML
    private Button cancel;
    @FXML
    private Button submit;
    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField title;



    @Override
    public void initializeFully() {
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
        submit.setOnAction(event -> createCourse());
    }

    public void createCourse() {
        CourseUtility.createCourse("create", title, startDate, endDate, this.getMainController(), errorMessage);
    }
}

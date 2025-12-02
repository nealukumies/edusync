package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for adding a new course.
 */
public class AddCourseController extends SubController {
    /** Label to display error messages */
    @FXML
    private Label errorMessage;
    /** Button to cancel the course creation */
    @FXML
    private Button cancel;
    /** Button to submit the course creation */
    @FXML
    private Button submit;
    /** DatePicker for course end date */
    @FXML
    private DatePicker endDate;
    /** DatePicker for course start date */
    @FXML
    private DatePicker startDate;
    /** TextField for course title */
    @FXML
    private TextField title;



    @Override
    public void initializeFully() {
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
        submit.setOnAction(event -> createCourse());
    }

    /**
     * Creates a new course using the provided details.
     */
    public void createCourse() {
        CourseUtility.createCourse("create", title, startDate, endDate, this.getMainController(), errorMessage);
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.db_objects.Course;

/**
 * Controller for editing a course.
 */
public class EditCourseController extends SubController {
    /** TextField for entering the course title */
    @FXML
    private TextField title;
    /** DatePicker for selecting the course start date */
    @FXML
    private DatePicker startDate;
    /** DatePicker for selecting the course end date */
    @FXML
    private DatePicker endDate;
    /** Submit button to create or update the course */
    @FXML
    private Button submit;
    /** Cancel button to go back to the previous page */
    @FXML
    private Button cancel;
    /** Label for displaying error messages */
    @FXML
    private Label errorMessage;
    /** The course being edited */
    private Course course;

    @Override
    public void initializeFully() {
        course = getMainController().getCourse();
        setStartInfo();
        submit.setOnAction(event -> createCourse());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
    }

    /** Sets the initial information for the course being edited. */
    public void setStartInfo() {
        title.setText(course.getCourseName());
        startDate.setValue(course.getStartDate());
        endDate.setValue(course.getEndDate());
    }

    /** Creates or updates the course using CourseUtility. */
    public void createCourse() {
        CourseUtility.createCourse("update", title, startDate, endDate, this.getMainController(), errorMessage);
    }
}

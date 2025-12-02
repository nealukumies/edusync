package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.db_objects.Course;

public class EditCourseController extends SubController {
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
    private Course course;

    @Override
    public void initializeFully() {
        course = getMainController().getCourse();
        setStartInfo();
        submit.setOnAction(event -> createCourse());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
    }

    public void setStartInfo() {
        title.setText(course.getCourseName());
        startDate.setValue(course.getStartDate());
        endDate.setValue(course.getEndDate());
    }

    public void createCourse() {
        CourseUtility.createCourse("update", title, startDate, endDate, this.getMainController(), errorMessage);
    }
}

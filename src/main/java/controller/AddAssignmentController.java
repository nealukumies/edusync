package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.db_objects.Course;

import java.util.List;

public class AddAssignmentController extends SubController {
    @FXML
    private Button cancel;
    @FXML
    private Button submit;
    @FXML
    private TextField timeSelect;
    @FXML
    private DatePicker dateSelect;
    @FXML
    private TextArea desc;
    @FXML
    private TextField title;
    @FXML
    private ChoiceBox<CourseOption> courseSelect;

    private int[] time = new int[2];
    private List<Course> courses;

    @Override
    public void initializeFully() {
        populateCourseList();
        submit.setOnAction(event -> createAssignment());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
    }

    private void populateCourseList() {
        AssignmentUtility.populateCourseList(courseSelect, courses, getMainController());
    }

    public void initialize() {
        try {
            courses = ParseHandler.getCourses();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize fully", e);
        }
    }

    public void createAssignment() {
        AssignmentUtility.submitAssignment(dateSelect, timeSelect, time, courseSelect, title, desc, getMainController());
    }
}

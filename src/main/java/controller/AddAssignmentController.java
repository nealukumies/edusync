package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.db_objects.Course;

import java.util.List;

/**
 * Controller for adding a new assignment.
 */
public class AddAssignmentController extends SubController {
    /** Button to cancel the assignment creation */
    @FXML
    private Button cancel;
    /** Button to submit the assignment */
    @FXML
    private Button submit;
    /** TextField for assignment time */
    @FXML
    private TextField timeSelect;
    /** DatePicker for assignment deadline */
    @FXML
    private DatePicker dateSelect;
    /** TextArea for assignment description */
    @FXML
    private TextArea desc;
    /** TextField for assignment title */
    @FXML
    private TextField title;
    /** ChoiceBox for selecting a course */
    @FXML
    private ChoiceBox<CourseOption> courseSelect;

    /** Time array to hold hour and minute */
    private int[] time = new int[2];
    /** List of courses fetched from the database */
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

    /**
     * Initializes the controller by fetching the list of courses.
     */
    public void initialize() {
        try {
            courses = ParseHandler.getCourses();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize fully", e);
        }
    }

    /**
     * Creates a new assignment using the AssignmentUtility class.
     */
    public void createAssignment() {
        AssignmentUtility.submitAssignment(dateSelect, timeSelect, time, courseSelect, title, desc, getMainController());
    }
}

package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.db_objects.Course;

import java.util.List;

/**
 * Controller for editing an assignment.
 */
public class EditAssignmentController extends SubController {
    /** ChoiceBox for selecting the course */
    @FXML
    private ChoiceBox<CourseOption> courseSelect;
    /** TextField for entering the assignment title */
    @FXML
    private TextField title;
    /** TextArea for entering the assignment description */
    @FXML
    private TextArea desc;
    /** DatePicker for selecting the assignment deadline date */
    @FXML
    private DatePicker dateSelect;
    /** TextField for selecting time in HH:MM:SS format */
    @FXML
    private TextField timeSelect;
    /** Submit button to create or update the assignment */
    @FXML
    private Button submit;
    /** Cancel button to go back to the previous page */
    @FXML
    private Button cancel;

    /** List of available courses */
    private List<Course> courses;
    /** Array to hold selected time (hours and minutes) */
    private int[] time = new int[2];

    /** Initializes the controller by fetching the list of courses. */
    public void initialize() {
        try {
            courses = ParseHandler.getCourses();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize", e);
        }
    }

    private void populateCourseList() {
        AssignmentUtility.populateCourseList(courseSelect, courses, getMainController());
    }

    @Override
    public void initializeFully() {
        populateCourseList();
        setStartingValues();
        submit.setOnAction(event -> createAssignment());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
    }

    /** Sets the starting values for the assignment fields based on the current assignment data. */
    public void setStartingValues() {
        final CourseOption opt = new CourseOption(getMainController().getCourse().getCourseId(), getMainController().getCourse().getCourseName());
        courseSelect.setValue(opt);
        title.setText(getMainController().getAssignment().getTitle());
        desc.setText(getMainController().getAssignment().getDescription());
        dateSelect.setValue(getMainController().getAssignment().getDeadline().toLocalDateTime().toLocalDate());
        final int[] timeInt = new int[2];
        final int hours = getMainController().getAssignment().getDeadline().toLocalDateTime().getHour();
        final int minutes = getMainController().getAssignment().getDeadline().toLocalDateTime().getMinute();
        timeInt[0] = hours;
        timeInt[1] = minutes;
        final String timeString = String.format("%02d", timeInt[0]) + ":" + String.format("%02d", timeInt[1]) + ":" + String.format("%02d", 0);
        timeSelect.setText(timeString);
    }

    /** Creates or updates an assignment based on user input. */
    public void createAssignment() {
        AssignmentUtility.submitAssignment(dateSelect, timeSelect, time, courseSelect, title, desc, getMainController());
    }
}

package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.db_objects.Course;

import java.util.List;

public class EditAssignmentController extends SubController {
    @FXML
    private ChoiceBox<CourseOption> courseSelect;
    @FXML
    private TextField title;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dateSelect;
    @FXML
    private TextField timeSelect;
    @FXML
    private Button submit;
    @FXML
    private Button cancel;

    private List<Course> courses;
    private int[] time = new int[2];

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

    public void createAssignment() {
        AssignmentUtility.submitAssignment(dateSelect, timeSelect, time, courseSelect, title, desc, getMainController());
    }
}

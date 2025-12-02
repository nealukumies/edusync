package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.db_objects.Course;
import model.handlers.AssignmentHandler;

import java.time.LocalDate;
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

    private int[] time = new int[2];
    private List<Course> courses;

    public void initialize() {
        try {
            courses = ParseHandler.getCourses();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize", e);
        }
    }

    private void populateCourseList() {
        courseSelect.getItems().clear();
        for (Course c : courses) {
            CourseOption option = new CourseOption(c.getCourseId(), c.getCourseName());
            courseSelect.getItems().add(option);
        }
        if (getMainController().getCourse() != null) {
            CourseOption selected = new CourseOption(getMainController().getCourse().getCourseId(), getMainController().getCourse().getCourseName());
            courseSelect.setValue(selected);
        }
    }

    @Override
    public void initializeFully() {
        populateCourseList();
        setStartingValues();
        submit.setOnAction(event -> createAssignment());
        cancel.setOnAction(event -> this.getMainController().goToPrevPage());
    }

    public void setStartingValues() {
        CourseOption opt = new CourseOption(getMainController().getCourse().getCourseId(), getMainController().getCourse().getCourseName());
        courseSelect.setValue(opt);
        title.setText(getMainController().getAssignment().getTitle());
        desc.setText(getMainController().getAssignment().getDescription());
        dateSelect.setValue(getMainController().getAssignment().getDeadline().toLocalDateTime().toLocalDate());
        int[] timeInt = new int[2];
        int hours = getMainController().getAssignment().getDeadline().toLocalDateTime().getHour();
        int minutes = getMainController().getAssignment().getDeadline().toLocalDateTime().getMinute();
        timeInt[0] = hours;
        timeInt[1] = minutes;
        String timeString = String.format("%02d", timeInt[0]) + ":" + String.format("%02d", timeInt[1]) + ":" + String.format("%02d", 0);
        timeSelect.setText(timeString);
    }

    public void createAssignment() {
        if (dateSelect.getValue() == null) {
            return;
        }

        try {
            parseTimeString(timeSelect.getText());
            CourseOption courseOption = courseSelect.getValue();
            String titleString = title.getText();
            String descString = desc.getText();
            LocalDate dateLocalDate = dateSelect.getValue();
            String timeString = String.format("%02d", time[0]) + ":" + String.format("%02d", time[1]) + ":" + String.format("%02d", 0);
            String dateTimeString = dateLocalDate.toString() + " " + timeString;
            AssignmentHandler.updateAssignment(getMainController().getAssignment().getAssignmentId(), courseOption.getId(), titleString, descString, dateTimeString, getMainController().getAssignment().getStatus().getDbValue());
            getMainController().goToPrevPage();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create assigment", e);
        }
    }

    public void parseTimeString(String ts) {
        if (ts.contains(":")) {
            try {
                String[] chars = ts.split(":");
                int hours = Integer.parseInt(chars[0]);
                int minutes = Integer.parseInt(chars[1]);
                time[0] = hours;
                time[1] = minutes;
                if (hours > 23 || hours < 0) {
                    time[0] = 0;
                }
                if (minutes > 59 || minutes < 0) {
                    time[1] = 0;
                }
            } catch (Exception e) {
                time[0] = 0;
                time[1] = 0;
            }
        }
    }
}

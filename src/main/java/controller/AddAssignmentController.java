package controller;

import component.CourseOption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import layout_model.ParseHandler;
import model.DBObjects.Course;
import model.handlers.AssignmentHandler;

import java.time.LocalDate;
import java.util.List;

public class AddAssignmentController extends SubController {
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
            throw new IllegalStateException("Failed to initialize fully", e);
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
        submit.setOnAction(event -> {
            createAssignment();
        });
        cancel.setOnAction(event -> {
            this.getMainController().goToPrevPage();
        });
    }

    public void createAssignment() {
        if (dateSelect.getValue() == null) {
            return;
        }

        try {
            parseTimeString(timeSelect.getText());
            CourseOption _course = courseSelect.getValue();
            String _title = title.getText();
            String _desc = desc.getText();
            LocalDate _date = dateSelect.getValue();
            String _time = String.format("%02d", time[0]) + ":" + String.format("%02d", time[1]) + ":" + String.format("%02d", 0);
            String _datetime = _date.toString() + " " + _time;
            AssignmentHandler.createAssignment(_course.getId(), _title, _desc, _datetime);
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

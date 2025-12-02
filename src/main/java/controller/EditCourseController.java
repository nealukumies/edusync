package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.db_objects.Course;
import model.handlers.CourseHandler;
import view.MainView;

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
        submit.setOnAction(event -> {
            createCourse();
        });
        cancel.setOnAction(event -> {
            this.getMainController().goToPrevPage();
        });

    }

    public void setStartInfo() {
        title.setText(course.getCourseName());
        startDate.setValue(course.getStartDate());
        endDate.setValue(course.getEndDate());
    }

    public boolean validateTitle() {
        return !title.getText().trim().isEmpty();
    }

    public boolean validateStartDate() {
        return startDate.getValue() != null;
    }

    public boolean validateEndDate() {
        return endDate.getValue() != null;
    }

    public void createCourse() {
        if (validateTitle() && validateStartDate() && validateEndDate()) {
            String _title = title.getText();
            String _startDate = startDate.getValue().toString();
            String _endDate = endDate.getValue().toString();
            try {
                CourseHandler.updateCourse(course.getCourseId(), _title, _startDate, _endDate);
                this.getMainController().changePage(Page.COURSE_LIST_PAGE);
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        } else {
            String errorText = MainView.getBundle().getString("course_errortext");
            errorMessage.setText(errorText);
        }
    }
}

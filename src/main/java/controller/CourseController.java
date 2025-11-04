package controller;

import component.AssignmentList;
import component.ViewableAssignment;
import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layout_model.ParseHandler;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.Schedule;
import model.handlers.CourseHandler;
import model.handlers.ScheduleHandler;
import view.MainView;

import java.util.List;
import java.util.Optional;

public class CourseController extends SubController {
    @FXML
    private Label name;
    @FXML
    private Label desc;
    @FXML
    private Hyperlink addSchedule;
    @FXML
    private Hyperlink addAssignment;
    @FXML
    private FlowPane assignments;
    @FXML
    private VBox scheduleContainer;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    private Course course = null;
    private List<Assignment> _assignments;
    private List<Schedule> schedules;

    @Override
    public void initializeFully() {
        course = getMainController().getCourse();
        if (course != null) {
            name.setText(course.getCourseName());
            String start = course.getStartDate().toString();
            String end = course.getEndDate().toString();
            String courseStarted = MainView.getBundle().getString("course_started");
            String courseEnding = MainView.getBundle().getString("course_ending");
            desc.setText(courseStarted + " " + start + " | " + courseEnding + " " + end);

            addAssignment.setOnMouseClicked(e -> {
                getMainController().changePage(Page.ADD_ASSIGNMENT_PAGE);
            });
            addSchedule.setOnMouseClicked(e -> {
                getMainController().changePage(Page.ADD_SCHEDULE_PAGE);
            });
            deleteBtn.setOnMouseClicked(e -> {
                deleteThisCourse();
            });
            editBtn.setOnMouseClicked(e -> {
                editThisCourse();
            });

            try {
                schedules = ParseHandler.getSchedulesForCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }

            displaySchedules();
            try {
                _assignments = ParseHandler.getAssignmentsForCourse(course);
                AssignmentList assignmentList = new AssignmentList(this.getMainController());
                assignments.getChildren().clear();
                TableView<ViewableAssignment> tableView = assignmentList.createList(_assignments, List.of(course));
                if (tableView != null) {
                    assignments.getChildren().add(tableView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displaySchedules() {
        scheduleContainer.getChildren().clear();
        if (schedules != null) {
            for (Schedule schedule : schedules) {
                HBox hBox = new HBox();
                Label scheduleDay = new Label(schedule.getWeekday().toString());
                Label scheduleTime = new Label("");
                String deleteText = MainView.getBundle().getString("delete");
                Hyperlink del = new Hyperlink(deleteText);
                String time = schedule.getStartTime().toString();
                time += " - " + schedule.getEndTime().toString();
                scheduleTime.setText(time);
                scheduleDay.getStyleClass().add("medium-text");
                scheduleTime.getStyleClass().add("medium-text");
                del.getStyleClass().add("medium-text");
                del.setOnAction(e -> {
                    deleteSchedule(schedule.getScheduleId());
                });
                hBox.setSpacing(30);
                hBox.getChildren().addAll(scheduleDay, scheduleTime, del);
                scheduleContainer.getChildren().add(hBox);
            }
        }
    }

    public void deleteSchedule(int scheduleId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String deleteScheduleText = MainView.getBundle().getString("delete_schedule_text");
        String deleteScheduleHeader = MainView.getBundle().getString("delete_schedule_header");
        String deleteScheduleContent = MainView.getBundle().getString("delete_schedule_content");

        alert.setTitle(deleteScheduleText);
        alert.setHeaderText(deleteScheduleHeader);
        alert.setContentText(deleteScheduleContent);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ScheduleHandler.deleteSchedule(scheduleId);
            // Refresh current page
            getMainController().goToPrevPage();
            getMainController().goToNextPage();
        }
    }

    public void deleteThisCourse() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String deleteCourseText = MainView.getBundle().getString("delete_course_text");
        String deleteCourseHeader = MainView.getBundle().getString("delete_course_header");
        String deleteCourseContent = MainView.getBundle().getString("delete_course_content");

        alert.setTitle(deleteCourseText);
        alert.setHeaderText(deleteCourseHeader);
        alert.setContentText(deleteCourseContent);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CourseHandler.deleteCourse(course.getCourseId());
            getMainController().setCourse(null);
            getMainController().goToPrevPage();
            getMainController().clearPageBackHistory();
            getMainController().updateButtons();
        }
    }
    
    public void editThisCourse() {
        getMainController().changePage(Page.EDIT_COURSE_PAGE);
    }
}

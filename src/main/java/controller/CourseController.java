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
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.db_objects.Schedule;
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
    private List<Schedule> schedules;

    @Override
    public void initializeFully() {
        course = getMainController().getCourse();
        if (course != null) {
            name.setText(course.getCourseName());
            final String start = course.getStartDate().toString();
            final String end = course.getEndDate().toString();
            final String courseStarted = MainView.getBundle().getString("course_started");
            final String courseEnding = MainView.getBundle().getString("course_ending");
            desc.setText(courseStarted + " " + start + " | " + courseEnding + " " + end);

            addAssignment.setOnMouseClicked(e -> getMainController().changePage(Page.ADD_ASSIGNMENT_PAGE));
            addSchedule.setOnMouseClicked(e -> getMainController().changePage(Page.ADD_SCHEDULE_PAGE));
            deleteBtn.setOnMouseClicked(e -> deleteThisCourse());
            editBtn.setOnMouseClicked(e -> editThisCourse());

            try {
                schedules = ParseHandler.getSchedulesForCourse(course);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to add initialize", e);
            }

            displaySchedules();
            try {
                final List<Assignment> assignmentsList = ParseHandler.getAssignmentsForCourse(course);
                final AssignmentList assignmentList = new AssignmentList(this.getMainController());
                assignments.getChildren().clear();
                final TableView<ViewableAssignment> tableView = assignmentList.createList(assignmentsList, List.of(course));
                if (tableView != null) {
                    assignments.getChildren().add(tableView);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Failed to initialize fully", e);
            }
        }
    }

    public void displaySchedules() {
        scheduleContainer.getChildren().clear();
        if (schedules != null) {
            for (final Schedule schedule : schedules) {
                final HBox hBox = new HBox();
                final Label scheduleDay = new Label(schedule.getWeekday().toString());
                final Label scheduleTime = new Label("");
                final String deleteText = MainView.getBundle().getString("delete");
                final Hyperlink del = new Hyperlink(deleteText);
                String time = schedule.getStartTime().toString();
                time += " - " + schedule.getEndTime().toString();
                scheduleTime.setText(time);
                final String textClass = "medium-text";
                scheduleDay.getStyleClass().add(textClass);
                scheduleTime.getStyleClass().add(textClass);
                del.getStyleClass().add(textClass);
                del.setOnAction(e -> deleteSchedule(schedule.getScheduleId()));
                hBox.setSpacing(30);
                hBox.getChildren().addAll(scheduleDay, scheduleTime, del);
                scheduleContainer.getChildren().add(hBox);
            }
        }
    }

    public void deleteSchedule(int scheduleId) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final String deleteScheduleText = MainView.getBundle().getString("delete_schedule_text");
        final String deleteScheduleHeader = MainView.getBundle().getString("delete_schedule_header");
        final String deleteScheduleContent = MainView.getBundle().getString("delete_schedule_content");

        alert.setTitle(deleteScheduleText);
        alert.setHeaderText(deleteScheduleHeader);
        alert.setContentText(deleteScheduleContent);
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ScheduleHandler.deleteSchedule(scheduleId);
            // Refresh current page
            getMainController().goToPrevPage();
            getMainController().goToNextPage();
        }
    }

    public void deleteThisCourse() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final String deleteCourseText = MainView.getBundle().getString("delete_course_text");
        final String deleteCourseHeader = MainView.getBundle().getString("delete_course_header");
        final String deleteCourseContent = MainView.getBundle().getString("delete_course_content");

        alert.setTitle(deleteCourseText);
        alert.setHeaderText(deleteCourseHeader);
        alert.setContentText(deleteCourseContent);
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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

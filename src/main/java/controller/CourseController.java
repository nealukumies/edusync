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
            desc.setText("Started on " + start + " | Ending on " + end);

            addAssignment.setOnMouseClicked(e -> {
                getMainController().changePage(Page.ADD_ASSIGNMENT_PAGE);
            });
            addSchedule.setOnMouseClicked(e -> {
                getMainController().changePage(Page.ADD_SCHEDULE_PAGE);
            });
            deleteBtn.setOnMouseClicked(e -> {
                deleteThisCourse();
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
                Hyperlink del = new Hyperlink("Delete");
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
        alert.setTitle("Delete Schedule");
        alert.setHeaderText("Delete Schedule?");
        alert.setContentText("Are you sure you want to delete this schedule?");
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
        alert.setTitle("Delete Course");
        alert.setHeaderText("Really Delete Course?");
        alert.setContentText("ARE YOU CERTAIN YOU WANT TO DELETE THIS COURSE?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CourseHandler.deleteCourse(course.getCourseId());
            getMainController().setCourse(null);
            getMainController().goToPrevPage();
            getMainController().clearPageBackHistory();
            getMainController().updateButtons();
        }
    }
}

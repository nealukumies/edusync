package controller;

import component.AssignmentList;
import component.ViewableAssignment;
import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layout_model.ParseHandler;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.Schedule;

import java.util.List;

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
                String time = schedule.getStartTime().toString();
                time += " - " + schedule.getEndTime().toString();
                scheduleTime.setText(time);
                scheduleDay.getStyleClass().add("medium-text");
                scheduleTime.getStyleClass().add("medium-text");
                hBox.setSpacing(30);
                hBox.getChildren().addAll(scheduleDay, scheduleTime);
                scheduleContainer.getChildren().add(hBox);
            }
        }
    }
}

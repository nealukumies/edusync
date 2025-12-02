package controller;

import component.AssignmentList;
import component.ViewableAssignment;
import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.db_objects.DBObjectParser;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;
import view.MainView;

import java.util.ArrayList;
import java.util.List;

public class DashboardController extends SubController {
    private List<Assignment> assignmentList;
    private List<Course> courses;

    @FXML
    private VBox assignments;
    @FXML
    private Hyperlink addAssignmentLink;
    @FXML
    private Hyperlink assignmentsLink;
    @FXML
    private Hyperlink coursesLink;
    @FXML
    private Hyperlink newCourseLink;
    @FXML
    private Hyperlink calendar;


    public void initialize() {
        this.assignmentList = new ArrayList<>();
        this.courses = new ArrayList<>();

        addAssignmentLink.setOnAction(e -> getMainController().changePage(Page.ADD_ASSIGNMENT_PAGE));
        assignmentsLink.setOnAction(e -> getMainController().changePage(Page.DASHBOARD_PAGE));
        coursesLink.setOnAction(e -> getMainController().changePage(Page.COURSE_LIST_PAGE));
        newCourseLink.setOnAction(e -> getMainController().changePage(Page.ADD_COURSE_PAGE));
        calendar.setOnAction(e -> getMainController().changePage(Page.CALENDAR_PAGE));
    }

    public void loadAssignments() {
        try {
            List<Assignment> data = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
            if (data != null) {
                assignmentList.addAll(data);
                generateAssignments();
            } else {
                String errorText = MainView.getBundle().getString("Assignments_not_found_error");
                throw new DataLoadException(errorText);
            }
        } catch (Exception e) {
            assignments.getChildren().clear();
            String labelText = MainView.getBundle().getString("no_assignments_label");
            Label label = new Label(labelText);
            assignments.getChildren().add(label);
        }
    }

    public void loadCourses() {
        try {
            List<Course> data = DBObjectParser.parseCourseList(CourseHandler.getCourses());
            if (data != null) {
                courses.addAll(data);
                generateAssignments();
            } else {
                String errorText = MainView.getBundle().getString("Assignments_not_found_error");
                throw new DataLoadException(errorText);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load courses", e);
        }
    }

    public void generateAssignments() {
        assignments.getChildren().clear();
        if (!assignmentList.isEmpty()) {
            AssignmentList al = new AssignmentList(getMainController());
            TableView<ViewableAssignment> table = al.createList(assignmentList, courses);
            assignments.getChildren().add(table);
            table.sort();
        }
    }

    @Override
    public void initializeFully() {
        loadAssignments();
        loadCourses();
    }
}

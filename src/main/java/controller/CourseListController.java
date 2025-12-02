package controller;

import component.CourseCard;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.db_objects.DBObjectParser;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;
import view.MainView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing and displaying the list of courses.
 */
public class CourseListController extends SubController {
    /** List of courses to be displayed */
    private List<Course> courses;
    /** The FlowPane that contains the list of course cards */
    @FXML
    private FlowPane courseListContent;
    /** The base VBox layout for the course list view */
    @FXML
    private VBox base;
    /** List of all assignments for the user */
    private List<Assignment> allAssignments;

    /** Initializes the controller and sets up the base layout. */
    public void initialize() {
        courses = new ArrayList<>();
        base.setFillWidth(true);
    }

    @Override
    public void initializeFully() {
        final ObservableValue<Double> limit = this.getMainController().getRoot().widthProperty().multiply(0.6).asObject();
        courseListContent.prefWrapLengthProperty().bind(limit);
        fetchAllAssignments();
        fetchCourseList();
    }

    /**
     * Fetches the list of courses from the CourseHandler and updates the UI accordingly.
     */
    public void fetchCourseList() {
        try {
            final HttpResponse<String> response = CourseHandler.getCourses();

            if (response == null) {
                courseListContent.getChildren().clear();
                final String labelText = MainView.getBundle().getString("no_courses_label");
                final Label emptyCourseList = new Label(labelText);
                courseListContent.getChildren().add(emptyCourseList);
                return;
            }

            this.courses = DBObjectParser.parseCourseList(response);
            courseListContent.getChildren().clear();
            generateCourseList();
        } catch (Exception e) {
            courseListContent.getChildren().clear();
            final String errorLabel = MainView.getBundle().getString("error_title");
            final Label label = new Label(errorLabel + ": " + e.getMessage());
            label.getStyleClass().add("error");
            label.setTextFill(Color.RED);
            courseListContent.getChildren().add(label);
        }
    }

    /**
     * Generates the course list UI by creating CourseCard components for each course.
     */
    public void generateCourseList() {
        courseListContent.getChildren().clear();

        for (final Course course : courses) {
            final CourseCard courseCard = new CourseCard(course, this, this.getMainController());
            courseListContent.getChildren().add(courseCard.create());
        }
    }

    /**
     * Fetches all assignments for the user and stores them in the allAssignments list.
     */
    public void fetchAllAssignments() {
        try {
            this.allAssignments = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to fetch assignments", e);
        }
    }

    /**
     * Gets all assignments for a specific course by its ID.
     *
     * @param id The ID of the course.
     * @return A list of assignments associated with the specified course.
     */
    public List<Assignment> getAssignmentsByCourse(final int id) {
        final List<Assignment> assignments = new ArrayList<>();
        for (final Assignment assignment : allAssignments) {
            if (assignment.getCourseId() == id) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

}

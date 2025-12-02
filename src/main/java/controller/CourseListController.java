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

public class CourseListController extends SubController {
    private List<Course> courses;
    @FXML
    private FlowPane courseListContent;
    @FXML
    private VBox base;
    private List<Assignment> allAssignments;

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

    public void generateCourseList() {
        courseListContent.getChildren().clear();

        for (final Course course : courses) {
            final CourseCard courseCard = new CourseCard(course, this, this.getMainController());
            courseListContent.getChildren().add(courseCard.create());
        }
    }

    public void fetchAllAssignments() {
        try {
            this.allAssignments = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to fetch assignments", e);
        }
    }

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

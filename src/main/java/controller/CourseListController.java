package controller;

import component.CourseCard;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.DBObjectParser;
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
        ObservableValue<Double> limit = this.getMainController().getRoot().widthProperty().multiply(0.6).asObject();
        courseListContent.prefWrapLengthProperty().bind(limit);
        fetchAllAssignments();
        fetchCourseList();
    }

    public void fetchCourseList() {
        try {
            HttpResponse<String> response = CourseHandler.getCourses();

            if (response == null) {
                courseListContent.getChildren().clear();
                String labelText = MainView.getBundle().getString("no_courses_label");
                Label emptyCourseList = new Label(labelText);
                courseListContent.getChildren().add(emptyCourseList);
                return;
            }

            this.courses = DBObjectParser.parseCourseList(response);
            courseListContent.getChildren().clear();
            generateCourseList();
        } catch (Exception e) {
            e.printStackTrace();
            courseListContent.getChildren().clear();
            String errorLabel = MainView.getBundle().getString("error_title");
            Label label = new Label(errorLabel + ": " + e.getMessage());
            label.getStyleClass().add("error");
            label.setTextFill(Color.RED);
            courseListContent.getChildren().add(label);
        }
    }

    public void generateCourseList() {
        courseListContent.getChildren().clear();

        for (Course course : courses) {
            System.out.println(this.getMainController());
            CourseCard courseCard = new CourseCard(course, this, this.getMainController());
            courseListContent.getChildren().add(courseCard.create());
        }
    }

    public void fetchAllAssignments() {
        try {
            this.allAssignments = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Assignment> getAssignmentsByCourse(int id) {
        List<Assignment> assignments = new ArrayList<>();
        for (Assignment assignment : allAssignments) {
            if (assignment.getCourseId() == id) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

}

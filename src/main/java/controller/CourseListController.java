package controller;

import component.CourseCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.DBObjectParser;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CourseListController extends SubController {
    private List<Course> courses;
    @FXML
    private FlowPane courseListContent;
    private List<Assignment> allAssignments;

    public void initialize() {
        courses = new ArrayList<>();
        fetchAllAssignments();
        fetchCourseList();
    }

    @Override
    public void initializeFully() {
    }

    public void fetchCourseList() {
        try {
            HttpResponse<String> response = CourseHandler.getCourses();

            if (response == null) {
                courseListContent.getChildren().clear();
                Label emptyCourseList = new Label("No courses found");
                courseListContent.getChildren().add(emptyCourseList);
                return;
            }

            this.courses = DBObjectParser.parseCourseList(response);
            courseListContent.getChildren().clear();
            generateCourseList();
        } catch (Exception e) {
            e.printStackTrace();
            courseListContent.getChildren().clear();
            Label label = new Label("Error: " + e.getMessage());
            label.getStyleClass().add("error");
            label.setTextFill(Color.RED);
            courseListContent.getChildren().add(label);
        }
    }

    public void generateCourseList() {
        courseListContent.getChildren().clear();

        for (Course course : courses) {
            CourseCard courseCard = new CourseCard(course, this);
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

package controller;

import component.AssignmentList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import layout_model.ParseHandler;
import model.DBObjects.Assignment;
import model.DBObjects.Course;

import java.util.List;

public class CourseController extends SubController {
    @FXML
    private Label name;
    @FXML
    private Label desc;
    @FXML
    private Hyperlink addAssignment;
    @FXML
    private FlowPane assignments;
    private Course course = null;
    private List<Assignment> _assignments;

    @Override
    public void initializeFully() {
        course = getMainController().getCourse();
        if (course != null) {
            name.setText(course.getCourseName());
            String start = course.getStartDate().toString();
            String end = course.getEndDate().toString();
            desc.setText("Started on " + start + " | Ending on " + end);
            try {
                _assignments = ParseHandler.getAssignmentsForCourse(course);
                AssignmentList assignmentList = new AssignmentList(this.getMainController());
                assignments.getChildren().clear();
                assignments.getChildren().add(assignmentList.createList(_assignments, List.of(course)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

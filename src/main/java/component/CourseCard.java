package component;

import controller.CourseListController;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.DBObjects.Assignment;
import model.DBObjects.Course;

import java.util.List;

public class CourseCard {
    private Course course;
    private CourseListController courseListController;

    public CourseCard(Course course, CourseListController courseListController) {
        this.course = course;
        this.courseListController = courseListController;
    }

    public VBox create() {
        VBox card = new VBox();
        card.setSpacing(10);
        card.getStyleClass().add("course-card");

        // Start by adding title
        Label courseName = new Label(course.getCourseName());
        courseName.setWrapText(true);
        courseName.getStyleClass().add("medium-title");

        // Add start and end dates
        Label courseDateTimeline = new Label("");
        courseDateTimeline.setWrapText(true);
        courseDateTimeline.getStyleClass().add("medium-text");
        String start = course.getStartDate().toString();
        String end = course.getEndDate().toString();
        courseDateTimeline.setText(start + " - " + end);

        // Add assignments
        Label assignmentsTitle = new Label("No Assignments");
        assignmentsTitle.getStyleClass().add("medium-text");
        assignmentsTitle.setWrapText(true);
        List<Assignment> assignments = courseListController.getAssignmentsByCourse(this.course.getCourseId());
        if (assignments != null) {
            assignmentsTitle.setText("Assignments (" + assignments.size() + ")");
        }

        card.getChildren().addAll(courseName, courseDateTimeline, assignmentsTitle);

        return card;
    }
}

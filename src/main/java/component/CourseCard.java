package component;

import controller.CourseListController;
import controller.MainController;
import enums.Page;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import view.MainView;

import java.util.List;

public class CourseCard {
    private Course course;
    private CourseListController courseListController;
    private MainController mainController;

    public CourseCard(Course course, CourseListController courseListController, MainController mainController) {
        this.course = course;
        this.courseListController = courseListController;
        this.mainController = mainController;
    }

    public VBox create() {
        VBox card = new VBox();
        card.setSpacing(10);
        card.getStyleClass().add("course-card");

        // Start by adding title
        Hyperlink courseName = new Hyperlink(course.getCourseName());
        courseName.setWrapText(true);
        courseName.getStyleClass().add("medium-title");

        courseName.setOnAction(e -> {
            mainController.setCourse(course);
            mainController.changePage(Page.COURSE_PAGE);
        });

        // Add start and end dates
        Label courseDateTimeline = new Label("");
        courseDateTimeline.setWrapText(true);
        courseDateTimeline.getStyleClass().add("medium-text");
        String start = course.getStartDate().toString();
        String end = course.getEndDate().toString();
        courseDateTimeline.setText(start + " - " + end);

        // Add assignments
        String assignmentText = MainView.getBundle().getString("No_Assignments");
        Label assignmentsTitle = new Label(assignmentText);
        assignmentsTitle.getStyleClass().add("medium-text");
        assignmentsTitle.setWrapText(true);
        List<Assignment> assignments = courseListController.getAssignmentsByCourse(this.course.getCourseId());
        if (assignments != null) {
            String assignmentsLabelText = MainView.getBundle().getString("Assignments");
            assignmentsTitle.setText(assignmentsLabelText + " \u200E(" + assignments.size() + ")");
        }

        card.getChildren().addAll(courseName, courseDateTimeline, assignmentsTitle);

        return card;
    }
}

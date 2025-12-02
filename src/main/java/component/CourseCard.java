package component;

import controller.CourseListController;
import controller.MainController;
import enums.Page;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.db_objects.Assignment;
import model.db_objects.Course;
import view.MainView;

import java.util.List;

/**
 *
 */
public class CourseCard {
    /** Course associated with this card */
    private Course course;
    /** CourseListController instance */
    private CourseListController courseListController;
    /** MainController instance */
    private MainController mainController;

    /** Constructor for CourseCard
     *
     * @param course Course associated with this card
     * @param courseListController CourseListController instance
     * @param mainController MainController instance
     */
    public CourseCard(final Course course, final CourseListController courseListController, final MainController mainController) {
        this.course = course;
        this.courseListController = courseListController;
        this.mainController = mainController;
    }

    /** Creates a VBox representing the course card
     *
     * @return VBox representing the course card
     */
    public VBox create() {
        final VBox card = new VBox();
        card.setSpacing(10);
        card.getStyleClass().add("course-card");

        // Start by adding title
        final Hyperlink courseName = new Hyperlink(course.getCourseName());
        courseName.setWrapText(true);
        courseName.getStyleClass().add("medium-title");

        courseName.setOnAction(e -> {
            mainController.setCourse(course);
            mainController.changePage(Page.COURSE_PAGE);
        });

        // Add start and end dates
        final Label courseDateTimeline = new Label("");
        courseDateTimeline.setWrapText(true);
        courseDateTimeline.getStyleClass().add("medium-text");
        final String start = course.getStartDate().toString();
        final String end = course.getEndDate().toString();
        courseDateTimeline.setText(start + " - " + end);

        // Add assignments
        final String assignmentText = MainView.getBundle().getString("No_Assignments");
        final Label assignmentsTitle = new Label(assignmentText);
        assignmentsTitle.getStyleClass().add("medium-text");
        assignmentsTitle.setWrapText(true);
        final List<Assignment> assignments = courseListController.getAssignmentsByCourse(this.course.getCourseId());
        if (assignments != null) {
            final String assignmentsLabelText = MainView.getBundle().getString("Assignments");
            assignmentsTitle.setText(assignmentsLabelText + " \u200E(" + assignments.size() + ")");
        }

        card.getChildren().addAll(courseName, courseDateTimeline, assignmentsTitle);

        return card;
    }
}

package component;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.DBObjects.Course;

public class CourseCard {
    private Course course;

    public CourseCard(Course course) {
        this.course = course;
    }

    public VBox create() {
        VBox card = new VBox();
        card.setSpacing(10);
        card.getStyleClass().add("course-card");

        // Start by adding title
        Label courseName = new Label(course.getCourseName());
        courseName.setWrapText(true);
        courseName.getStyleClass().add("medium-title");

        // Add description (placeholder)
        Label courseDesc = new Label("PLACEHOLDER");
        courseDesc.setWrapText(true);
        courseDesc.getStyleClass().add("medium-text");

        card.getChildren().addAll(courseName, courseDesc);

        return card;
    }
}

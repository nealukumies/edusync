package controller;

import component.CourseCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import model.DBObjects.Course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseListController extends SubController {
    private List<Course> courses;
    @FXML
    private FlowPane courseListContent;

    public void initialize() {
        courses = new ArrayList<>();
    }

    @Override
    public void initializeFully() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Course dummyA = new Course(0, 0, "Data Analytics", sdf.parse("2025-09-01"), sdf.parse("2025-11-02"));
            Course dummyB = new Course(1, 0, "Physics", sdf.parse("2025-09-01"), sdf.parse("2025-11-02"));
            courses.add(dummyA);
            courses.add(dummyB);
            generateCourseList();
        }
        catch (Exception e) {
            Label  label = new Label("Error: " + e.getMessage());
            label.setTextFill(Color.RED);
            courseListContent.getChildren().add(label);
        }
    }

    public void generateCourseList() {
        courseListContent.getChildren().clear();

        for  (Course course : courses) {
            CourseCard courseCard = new CourseCard(course);
            courseListContent.getChildren().add(courseCard.create());
        }
    }
}

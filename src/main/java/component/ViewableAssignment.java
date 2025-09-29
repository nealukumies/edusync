package component;

import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.Enums.Status;

import java.sql.Timestamp;

public class ViewableAssignment {
    private Assignment assignment;
    private Course course;

    public ViewableAssignment(Assignment assignment, Course course) {
        this.assignment = assignment;
        this.course = course;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Course getCourseObject() {
        return course;
    }

    public String getTitle() {
        return assignment.getTitle();
    }

    public String getCourse() {
        return course.getCourseName();
    }

    public Timestamp getDeadline() {
        return assignment.getDeadline();
    }

    public Status getStatus() {
        return assignment.getStatus();
    }
}

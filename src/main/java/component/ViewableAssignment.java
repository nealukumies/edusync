package component;

import model.db_objects.Assignment;
import model.db_objects.Course;
import model.enums.Status;

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

    public Integer getCourse() {
        return course.getCourseId();
    }

    public Timestamp getDeadline() {
        return assignment.getDeadline();
    }

    public Status getStatus() {
        return assignment.getStatus();
    }
}

package component;

import model.db_objects.Assignment;
import model.db_objects.Course;
import model.enums.Status;
import view.MainView;

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

    public String getStatus() {
        Status status = assignment.getStatus();
        String statusString = "error";

        switch (status) {
            case PENDING -> statusString = MainView.getBundle().getString("PENDING");
            case IN_PROGRESS -> statusString = MainView.getBundle().getString("IN_PROGRESS");
            case COMPLETED -> statusString = MainView.getBundle().getString("COMPLETED");
            case OVERDUE -> statusString = MainView.getBundle().getString("OVERDUE");
        }

        return statusString;
    }
}

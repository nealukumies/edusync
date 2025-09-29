package component;

import model.Enums.Status;

import java.sql.Timestamp;

public class ViewableAssignment {
    private String title;
    private int course;
    private Timestamp deadline;
    private Status status;
    private String courseName;

    public ViewableAssignment(String title, int course, Timestamp deadline, Status status, String courseName) {
        this.title = title;
        this.course = course;
        this.deadline = deadline;
        this.status = status;
        this.courseName = courseName;
    }

    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return courseName;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }
}

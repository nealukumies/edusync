package component;

import model.Enums.Status;

import java.sql.Timestamp;
import java.time.LocalDate;

public class ViewableAssignment {
    private String title;
    private int course;
    private Timestamp deadline;
    private Status status;

    public ViewableAssignment(String title, int course, Timestamp deadline, Status status) {
        this.title = title;
        this.course = course;
        this.deadline = deadline;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public int getCourse() {
        return course;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public Status getStatus() {
        return status;
    }
}

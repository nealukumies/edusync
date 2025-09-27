package component;

import model.Enums.Status;

import java.time.LocalDate;
import java.util.Date;

public class ViewableAssignment {
    private String title;
    private int course;
    private LocalDate deadline;
    private Status status;

    public ViewableAssignment(String title, int course, LocalDate deadline, Status status) {
        this.title = title;
        this.course = course;
        this.deadline = deadline;
        this.status = status;
    }

    public String getTitle() { return title; }
    public int getCourse() { return course; }
    public LocalDate getDeadline() { return deadline; }
    public Status getStatus() { return status; }
}

package component;

import model.Enums.Status;

import java.util.Date;

public class ViewableAssignment {
    private String title;
    private int course;
    private Date deadline;
    private Status status;

    public ViewableAssignment(String title, int course, Date deadline, Status status) {
        this.title = title;
        this.course = course;
        this.deadline = deadline;
        this.status = status;
    }

    public String getTitle() { return title; }
    public int getCourse() { return course; }
    public Date getDeadline() { return deadline; }
    public Status getStatus() { return status; }
}

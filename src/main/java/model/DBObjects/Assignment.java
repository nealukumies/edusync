// This class represents an assignment entity in the system.

package model.DBObjects;

import model.Enums.Status;

import java.util.Date;

public class Assignment {
    private int assignmentId;
    private int studentId;
    private Integer courseId;
    private String title;
    private String description;
    private Date deadline;
    private Status status;

    public Assignment(int assignmentId, int studentId, Integer courseId, String title, String description, Date deadline, Status status) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.courseId = courseId; //note that courseId is nullable in the database
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public int getAssignmentId() {
        return assignmentId;
    }
    public int getStudentId() {
        return studentId;
    }
    public Integer getCourseId() {
        return courseId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDeadline() {
        return deadline;
    }
    public Status getStatus() {
        return status;
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}

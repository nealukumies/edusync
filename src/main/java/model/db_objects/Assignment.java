// This class represents an assignment entity in the system.

package model.db_objects;

import model.enums.Status;

import java.sql.Timestamp;

/** * Represents an assignment entity in the system.
 */
public class Assignment {
    /**  * The unique identifier for the assignment.
     */
    private int assignmentId;
    /**  * The ID of the student to whom the assignment belongs.
     */
    private int studentId;
    /**  * The ID of the course associated with the assignment (nullable).
     */
    private Integer courseId;
    /**  * The title of the assignment.
     */
    private String title;
    /**  * A brief description of the assignment.
     */
    private String description;
    /**  * The deadline for the assignment.
     */
    private Timestamp deadline;
    /**  * The current status of the assignment.
     */
    private Status status;

    /**
     * Constructs an Assignment object with the specified details.
     *
     * @param assignmentId The unique identifier for the assignment.
     * @param studentId    The ID of the student to whom the assignment belongs.
     * @param courseId     The ID of the course associated with the assignment (nullable).
     * @param title        The title of the assignment.
     * @param description  A brief description of the assignment.
     * @param deadline     The deadline for the assignment.
     * @param status       The current status of the assignment.
     */
    public Assignment(final int assignmentId, final int studentId, final Integer courseId, final String title, final String description, final Timestamp deadline, final Status status) {
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
    public Timestamp getDeadline() {
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

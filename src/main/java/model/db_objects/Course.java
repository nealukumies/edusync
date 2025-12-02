package model.db_objects;

import java.time.LocalDate;

/** Represents a course in the system. */
public class Course {
    /** The unique identifier for the course. */
    private int courseId;
    /** The identifier for the student enrolled in the course. */
    private int studentId;
    /** The name of the course. */
    private String courseName;
    /** The start date of the course. */
    private LocalDate startDate;
    /** The end date of the course. */
    private LocalDate endDate;

    /** Constructor for Course.
     *
     * @param courseId   The unique identifier for the course.
     * @param studentId  The identifier for the student enrolled in the course.
     * @param courseName The name of the course.
     * @param startDate  The start date of the course.
     * @param endDate    The end date of the course.
     */
    public Course(final int courseId, final int studentId, final String courseName, final LocalDate startDate, final LocalDate endDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", studentId=" + studentId +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public int getCourseId() {
        return courseId;
    }
    public int getStudentId() {
        return studentId;
    }
    public String getCourseName() {
        return courseName;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
}

package model.db_objects;

import java.time.LocalDate;

public class Course {
    private int courseId;
    private int studentId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;

    public Course(int courseId, int studentId, String courseName, LocalDate startDate, LocalDate endDate) {
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

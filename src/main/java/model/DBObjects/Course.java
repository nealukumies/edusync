package model.DBObjects;

import java.util.Date;

public class Course {
    private int courseId;
    private int studentId;
    private String courseName;
    private Date startDate;
    private Date endDate;

    public Course(int courseId, int studentId, String courseName, Date startDate, Date endDate) {
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
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
}

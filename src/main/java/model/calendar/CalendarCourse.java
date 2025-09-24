package model.calendar;

import java.time.LocalDate;

public class CalendarCourse {


    int courseId;
    int studentId;
    String courseName;
    LocalDate startDate;
    LocalDate endDate;

    public CalendarCourse(int courseId, int studentId, String courseName, LocalDate startDate, LocalDate endDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isDuringCourse(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}

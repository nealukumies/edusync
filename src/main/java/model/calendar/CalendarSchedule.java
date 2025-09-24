package model.calendar;

import java.time.LocalDate;

public class CalendarSchedule {
    int scheduleId;
    int courseId;
    String weekday;
    int startTime;
    int endTime;

    CalendarCourse  calendarCourse;

    public CalendarSchedule(int scheduleId, int courseId, String weekday, int startTime, int endTime) {
        this.scheduleId = scheduleId;
        this.courseId = courseId;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getWeekday() {
        return weekday;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public CalendarCourse getCalendarCourse() {
        return calendarCourse;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setCalendarCourse(CalendarCourse calendarCourse) {
        this.calendarCourse = calendarCourse;
    }

    public boolean isDuringSchedule(int clock){
        return startTime <= clock && clock <= endTime;
    }
}
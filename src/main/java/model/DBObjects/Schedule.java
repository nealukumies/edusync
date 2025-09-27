package model.DBObjects;

import model.Enums.Weekday;

import java.time.LocalTime;

public class Schedule {
    private int scheduleId;
    private int courseId;
    private Weekday weekday;
    private LocalTime startTime;
    private LocalTime endTime;

    public Schedule(int scheduleId, int courseId, Weekday weekday, LocalTime startTime, LocalTime endTime) {
        this.scheduleId = scheduleId;
        this.courseId = courseId;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", courseId=" + courseId +
                ", weekday=" + weekday +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public int getScheduleId() {
        return scheduleId;
    }
    public int getCourseId() {
        return courseId;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
}

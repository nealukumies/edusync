package model.db_objects;

import model.enums.Weekday;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    static String startTimeString = "09:00";
    static String endTimeString = "10:00";

    @Test
    void testToString() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(startTimeString));
        final String expected = "Schedule{scheduleId=1, courseId=101, weekday=MONDAY, startTime='09:00', endTime='09:00'}";
        assertEquals(expected, schedule.toString());
    }

    @Test
    void getScheduleId() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(endTimeString));
        assertEquals(1, schedule.getScheduleId());
    }

    @Test
    void getCourseId() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(endTimeString));
        assertEquals(101, schedule.getCourseId());
    }

    @Test
    void getWeekday() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(endTimeString));
        assertEquals(Weekday.MONDAY, schedule.getWeekday());
    }

    @Test
    void getStartTime() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(endTimeString));
        assertEquals(LocalTime.parse(startTimeString), schedule.getStartTime());
    }

    @Test
    void getEndTime() {
        final Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse(startTimeString), LocalTime.parse(endTimeString));
        assertEquals(LocalTime.parse(endTimeString), schedule.getEndTime());
    }
}
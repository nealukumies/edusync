package model.DBObjects;

import model.Enums.Weekday;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    void testToString() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("09:00"));
        String expected = "Schedule{scheduleId=1, courseId=101, weekday=MONDAY, startTime='09:00', endTime='09:00'}";
        assertEquals(expected, schedule.toString());
    }

    @Test
    void getScheduleId() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        assertEquals(1, schedule.getScheduleId());
    }

    @Test
    void getCourseId() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        assertEquals(101, schedule.getCourseId());
    }

    @Test
    void getWeekday() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        assertEquals(Weekday.MONDAY, schedule.getWeekday());
    }

    @Test
    void getStartTime() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        assertEquals(LocalTime.parse("09:00"), schedule.getStartTime());
    }

    @Test
    void getEndTime() {
        Schedule schedule = new Schedule(1, 101, Weekday.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00"));
        assertEquals(LocalTime.parse("10:00"), schedule.getEndTime());
    }
}
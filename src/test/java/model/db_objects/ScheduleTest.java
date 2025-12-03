package model.db_objects;

import model.enums.Weekday;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for Schedule.
 */
class ScheduleTest {
    /** The start time string for testing.
     */
    static String startTimeString = "09:00";
    /** The end time string for testing.
     */
    static String endTimeString = "10:00";

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
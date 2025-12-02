package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleHandlerTest {

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
    }

    @Test
    void getSchedule() {
        assertNull(ScheduleHandler.getSchedule(145));
    }

    @Test
    void getScheduleNotFound() {
        assertNull(ScheduleHandler.getSchedule(99999));
    }

    @Test //x
    void getScheduleNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertNull(ScheduleHandler.getSchedule(145));
    }

    @Test
    void getSchedulesForCourse() {
        assertNotNull(ScheduleHandler.getSchedulesForCourse(174).body());
    }

    @Test
    void getSchedulesForCourseNotFound() {
        assertNull(ScheduleHandler.getSchedulesForCourse(99999));
    }

    @Test //x
    void getSchedulesForCourseNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertNotNull(ScheduleHandler.getSchedulesForCourse(174));
    }

    @Test
    void getSchedulesForUser() {
        assertNotNull(ScheduleHandler.getSchedulesForUser().body());
    }

    @Test
    void getSchedulesForUserNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertNull(ScheduleHandler.getSchedulesForUser());
    }

    @Test
    void createSchedule() throws JsonProcessingException {
        int scheduleId = ScheduleHandler.createSchedule(174, "Monday", "10:00", "12:00");
        assertTrue(scheduleId > 0);

        ScheduleHandler.deleteSchedule(scheduleId);
    }

    @Test
    void createScheduleInvalidInput() throws JsonProcessingException {
        assertEquals(-1, ScheduleHandler.createSchedule(-1, null, "10:00", "12:00"));
    }

    @Disabled //BROKEN
    void createScheduleNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(-1, ScheduleHandler.createSchedule(174, "Monday", "10:00", "12:00"));
    }

    @Test
    void updateSchedule() {
        assertEquals(1, ScheduleHandler.updateSchedule(145, 174, "Monday", "11:00", "13:00"));
    }

    @Test
    void updateScheduleNotFound() {
        assertEquals(-1, ScheduleHandler.updateSchedule(99999, 174, "Monday", "11:00", "13:00"));
    }

    @Test //x
    void updateScheduleNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertEquals(1, ScheduleHandler.updateSchedule(145, 174, "Monday", "11:00", "13:00"));
    }

    @Test
    void deleteSchedule() throws JsonProcessingException {
        int scheduleId = ScheduleHandler.createSchedule(174, "Monday", "10:00", "12:00");

        assertEquals(1, ScheduleHandler.deleteSchedule(scheduleId));
    }

    @Test
    void deleteScheduleNotFound() {
        assertEquals(-1, ScheduleHandler.deleteSchedule(99999));
    }

    @Disabled //BROKEN
    void deleteScheduleNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertEquals(-1, ScheduleHandler.deleteSchedule(143));
    }
}
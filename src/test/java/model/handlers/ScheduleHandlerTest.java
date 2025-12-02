package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleHandlerTest {
    static String mondayString = "Monday";
    static String startTime10String = "10:00";
    static String startTime11String = "11:00";
    static String endTime12String = "12:00";
    static String endTime13String = "13:00";

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
        int scheduleId = ScheduleHandler.createSchedule(174, mondayString, startTime10String, endTime12String);
        assertTrue(scheduleId > 0);

        ScheduleHandler.deleteSchedule(scheduleId);
    }

    @Test
    void createScheduleInvalidInput() throws JsonProcessingException {
        assertEquals(-1, ScheduleHandler.createSchedule(-1, null, startTime10String, endTime12String));
    }

    @Test
    void updateSchedule() {
        assertEquals(1, ScheduleHandler.updateSchedule(145, 174, mondayString, startTime11String, endTime13String));
    }

    @Test
    void updateScheduleNotFound() {
        assertEquals(-1, ScheduleHandler.updateSchedule(99999, 174, mondayString, startTime11String, endTime13String));
    }

    @Test //x
    void updateScheduleNotLoggedIn() {
        Account.getInstance().clearAccount();
        assertEquals(1, ScheduleHandler.updateSchedule(145, 174, mondayString, startTime11String, endTime13String));
    }

    @Test
    void deleteSchedule() throws JsonProcessingException {
        int scheduleId = ScheduleHandler.createSchedule(174, mondayString, startTime10String, endTime12String);

        assertEquals(1, ScheduleHandler.deleteSchedule(scheduleId));
    }

    @Test
    void deleteScheduleNotFound() {
        assertEquals(-1, ScheduleHandler.deleteSchedule(99999));
    }

}
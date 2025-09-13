/**
 * Unit tests for ScheduleDao class.
 */
package database;

import model.Schedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ScheduleDaoTest {
    private static ScheduleDao scheduleDao;
    private List<Integer> insertedSchedules;

    /**
     * Setup DAO before all tests.
     */
    @BeforeAll
    static void setup() {
        scheduleDao = new ScheduleDao();
    }

    /**
     * Initialize the inserted id list before each test to track inserted schedules for cleanup.
     */
    @BeforeEach
    void init() {
        insertedSchedules = new ArrayList<>();
    }

    /**
     * Delete inserted schedules after each test.
     */
    @AfterEach
    void cleanup() {
        for (int id : insertedSchedules) {
            scheduleDao.deleteSchedule(id);
        }
        insertedSchedules.clear();
    }

    /**
     * Test inserting and deleting a schedule. Deletes the inserted schedule after testing.
     */
    @Test
    void insertAndDeleteScheduleTest() {
        int id = scheduleDao.insertSchedule(1, model.Weekday.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        assertTrue(scheduleDao.deleteSchedule(id), "Deletion successful for ID: " + id);
    }

    /**
     * Test deleting a schedule with a non-existent ID. The deletion should fail.
     */
    @Test
    void insertScheduleWithNullCourseIdTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scheduleDao.insertSchedule(null, model.Weekday.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));
        });
        String expectedMessage = "Course ID, weekday, start time, and end time must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message should indicate null parameters");
    }

    /**
     * Test inserting a schedule with a null weekday. Should throw IllegalArgumentException.
     */
    @Test
    void insertScheduleWithNullWeekdayTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scheduleDao.insertSchedule(1, null, LocalTime.of(10, 0), LocalTime.of(11, 0));
        });
        String expectedMessage = "Course ID, weekday, start time, and end time must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message should indicate null parameters");
    }

    /**
     * Test inserting a schedule with start time after end time. Should throw IllegalArgumentException.
     */
    @Test
    void insertScheduleWithStartTimeAfterEndTimeTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scheduleDao.insertSchedule(1, model.Weekday.MONDAY, LocalTime.of(12, 0), LocalTime.of(11, 0));
        });
        String expectedMessage = "Start time must be before end time";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message should indicate start time after end time");
    }


    /**
     * Test to get a schedule by its ID. Inserts a schedule first, then retrieves it.
     */
    @Test
    void getScheduleTest() {
        int id = scheduleDao.insertSchedule(1, model.Weekday.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedSchedules.add(id);
        Schedule schedule = scheduleDao.getSchedule(id);
        assertNotNull(schedule, "Schedule should not be null");
        assertEquals(id, schedule.getScheduleId(), "Schedule ID should match");
        assertEquals(1, schedule.getCourseId(), "Course ID should match");
        assertEquals(model.Weekday.TUESDAY, schedule.getWeekday(), "Weekday should match");
        assertEquals(LocalTime.of(9, 0), schedule.getStartTime(), "Start time should match");
        assertEquals(LocalTime.of(10, 0), schedule.getEndTime(), "End time should match");
    }

    /**
     * Test retrieving a schedule with an invalid ID. The result should be null.
     */
    @Test
    void getScheduleWithInvalidIdTest() {
        Schedule schedule = scheduleDao.getSchedule(-100);
        assertNull(schedule, "Schedule should be null for non-existent ID");
    }

    /**
     * Test retrieving all schedules for a specific course. Inserts two schedules for the course, then retrieves them.
     * Cleans up by deleting the course. cleanup() will delete the inserted schedules.
     */
    @Test
    void getAllSchedulesForCourseTest() {
        CourseDao courseDao = new CourseDao();
        int courseId= courseDao.addCourse(1, "Test101", Date.valueOf("2025-01-01"), Date.valueOf("2025-06-01"));
        assertTrue(courseId > 0, "Course insertion should be succesfull");
        int scheduleId1 = scheduleDao.insertSchedule(courseId, model.Weekday.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));
        int scheduleId2 = scheduleDao.insertSchedule(courseId, model.Weekday.FRIDAY, LocalTime.of(14, 0), LocalTime.of(15, 0));
        assertTrue(scheduleId1 > 0 && scheduleId2 > 0, "Schedule insertions should be successful");
        insertedSchedules.add(scheduleId1);
        insertedSchedules.add(scheduleId2);
        List<Schedule> schedules = scheduleDao.getAllSchedulesForCourse(courseId);
        assertNotNull(schedules, "Schedules list should not be null");
        assertEquals(2, schedules.size(), "Should retrieve 2 schedules");
        List<Integer> retrievedIds = schedules.stream().map(Schedule::getScheduleId).toList();
        assertTrue(retrievedIds.contains(scheduleId1), "Should contain scheduleId1");
        assertTrue(retrievedIds.contains(scheduleId2), "Should contain scheduleId2");
        courseDao.deleteCourse(courseId);
    }

    /**
     * Test retrieving schedules for a course with no schedules. The result should be an empty list.
     */
    @Test
    void getAllSchedulesForCourseWithNoSchedulesTest() {
        CourseDao courseDao = new CourseDao();
        int courseId= courseDao.addCourse(1, "Test101", Date.valueOf("2025-01-01"), Date.valueOf("2025-06-01"));
        List<Schedule> schedules = scheduleDao.getAllSchedulesForCourse(courseId);
        assertNotNull(schedules, "Schedules list should not be null");
        assertEquals(0, schedules.size(), "Should retrieve 0 schedules");
        courseDao.deleteCourse(courseId);
    }

    /**
     *
     */
    @Test
    void getAllSchedulesForCourseWithInvalidIdTest() {
        List<Schedule> schedules = scheduleDao.getAllSchedulesForCourse(-100);
        assertNotNull(schedules, "Schedules list should not be null");
        assertEquals(0, schedules.size(), "Should retrieve 0 schedules for non-existent course ID");
    }

    /**
     * Test retrieving all schedules for a specific student. Inserts a student, a course for that student, and two schedules for the course.
     * Then retrieves the schedules for the student. Cleans up by deleting the course and student. cleanup() will delete the inserted schedules.
     */
    @Test
    void getAllSchedulesForStudentTest() {
        StudentDao studentDao = new StudentDao();
        CourseDao courseDao = new CourseDao();
        int studentId = studentDao.addStudent("testuser", "test@cases.com");
        int courseId = courseDao.addCourse(studentId, "Test101", Date.valueOf("2025-01-01"), Date.valueOf("2025-06-01"));
        assertTrue(studentId > 0 && courseId > 0, "Student and Course insertion should be successful");
        int scheduleId1 = scheduleDao.insertSchedule(courseId, model.Weekday.THURSDAY, LocalTime.of(10, 0), LocalTime.of(11, 0));
        int scheduleId2 = scheduleDao.insertSchedule(courseId, model.Weekday.FRIDAY, LocalTime.of(14, 0), LocalTime.of(15, 0));
        assertTrue(scheduleId1 > 0 && scheduleId2 > 0, "Schedule insertions should be successful");
        insertedSchedules.add(scheduleId1);
        insertedSchedules.add(scheduleId2);
        List<Schedule> schedules = scheduleDao.getAllSchedulesForStudent(studentId);
        assertNotNull(schedules, "Schedules list should not be null");
        assertEquals(2, schedules.size(), "Should retrieve 2 schedules");
        List<Integer> retrievedIds = schedules.stream().map(Schedule::getScheduleId).toList();
        assertTrue(retrievedIds.contains(scheduleId1), "Should contain scheduleId1");
        assertTrue(retrievedIds.contains(scheduleId2), "Should contain scheduleId2");
        courseDao.deleteCourse(courseId);
        studentDao.deleteStudent(studentId);
    }

    /**
     * Test retrieving schedules for a student with no schedules. The result should be an empty list.
     */
    @Test
    void getAllSchedulesForStudentWithNoSchedulesTest() {
        StudentDao studentDao = new StudentDao();
        int studentId = studentDao.addStudent("Testuser", "email@email.email");
        List<Schedule> schedules = scheduleDao.getAllSchedulesForStudent(studentId);
        assertNotNull(schedules, "Schedules list should not be null");
        assertTrue(schedules.isEmpty(), "Schedules list should be empty");
        studentDao.deleteStudent(studentId);
    }

    /**
     * Test retrieving schedules for a student with an invalid ID. The result should be an empty list.
     */
    @Test
    void getAllSchedulesForStudentWithInvalidIdTest() {
        List<Schedule> schedules = scheduleDao.getAllSchedulesForStudent(-100);
        assertNotNull(schedules, "Schedules list should not be null");
        assertTrue(schedules.isEmpty(), "Schedules list should be empty");
    }
}
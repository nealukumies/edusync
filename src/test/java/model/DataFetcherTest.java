package model;

import database.AssignmentDao;
import database.CourseDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.DataFetcher.fetchCourse;
import static org.junit.jupiter.api.Assertions.*;

class DataFetcherTest {

    @Test
    void fetchUserAssignmentsForWorkingId() {
        int UserId = 1;
        List<Assignment> assignments = DataFetcher.fetchUserAssignments(UserId);
        assertFalse(assignments.isEmpty());
    }

    @Test
    void fetchUserAssignmentsForInvalidId() {
        int UserId = -1;
        List<Assignment> assignments = DataFetcher.fetchUserAssignments(UserId);
        assertTrue(assignments.isEmpty());
    }

    @Test
    void fetchCourseWithWorkingId() {
        Course course = fetchCourse(1);
        assertEquals(1, course.getCourseId());
    }

    @Test
    void fetchCourseWithInvalidId() {
        Course course = fetchCourse(-1);
        assertNull(course);
    }
}
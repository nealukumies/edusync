package model;

import database.AssignmentDao;
import database.CourseDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataFetcherTest {

    private AssignmentDao assignmentDao;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() {
        assignmentDao = new AssignmentDao();
        courseDao = new CourseDao();
    }

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
        Course course = courseDao.getCourseById(1);
        assertEquals(1, course.getCourseId());
    }

    @Test
    void fetchCourseWithInvalidId() {
        Course course = courseDao.getCourseById(-1);
        assertNull(course);
    }
}
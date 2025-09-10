/**
 * Unit tests for CourseDao class.
 */
package database;

import model.Course;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoTest {
    private static CourseDao courseDao;
    private List<Integer> insertedCourses;

    /**
     * Setup DAO before all tests.
     */
    @BeforeAll
    static void setup() {
        courseDao = new CourseDao();
    }

    /**
     * Initialize the inserted id list before each test to track inserted courses for cleanup.
     */
    @BeforeEach
    void init() {
        insertedCourses = new ArrayList<>();
    }

    /**
     * Delete inserted courses after each test.
     */
    @AfterEach
    void cleanup() {
        for (int id : insertedCourses) {
            courseDao.deleteCourse(id);
        }
        insertedCourses.clear();
    }

    /**
     * Test adding and deleting a course. Deletes the inserted course after testing.
     */
    @Test
    void addAndDeleteCourseTest() {
        int id = courseDao.addCourse(1, "Test101", Date.valueOf("2025-01-01"), Date.valueOf("2025-06-01"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        assertTrue(courseDao.deleteCourse(id), "Deletion successful for ID: " + id);
    }

    /**
     * Test deleting a course with a non-existent ID. The deletion should fail.
     */
    @Test
    void deleteCourseFailTest() {
        boolean result = courseDao.deleteCourse(-100); // Assuming -100 is an invalid ID
        assertFalse(result, "Deletion should fail for non-existent ID");
    }

    /**
     * Test adding a course with an invalid student ID. The insertion should fail.
     */
    @Test
    void addCourseWithInvalidStudentId() {
        int id = courseDao.addCourse(-200, "Test101", Date.valueOf("2025-04-01"), Date.valueOf("2025-10-01"));
        assertEquals(-1, id, "Insertion should fail with invalid student ID");
    }

    /**
     * Test adding a course where the end date is before the start date. The insertion should fail.
     */
    @Test
    void addCourseWithEndDateBeforeStartDate() {
        int id = courseDao.addCourse(1, "Test101", Date.valueOf("2025-12-01"), Date.valueOf("2025-01-01"));
        assertEquals(-1, id, "Insertion should fail when end date is before start date");
    }

    /**
     * Test adding a course with a null (empty) name. The insertion should fail.
     */
    @Test
    void addCourseWithNullName() {
        int id = courseDao.addCourse(1, "", Date.valueOf("2025-02-01"), Date.valueOf("2025-12-01"));
        assertEquals(-1, id, "Insertion should fail with null course name");
    }

    /**
     * Test retrieving an existing course by ID. The course should be found and details should match.
     */
    @Test
    void getCourseById() {
        int id = courseDao.addCourse(1, "Test101", Date.valueOf("2025-02-01"), Date.valueOf("2025-12-01"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedCourses.add(id);

        Course course = courseDao.getCourseById(id);
        assertNotNull(course, "Course should not be null");
        assertEquals("Test101", course.getCourseName(), "Course name should match");
        assertEquals(Date.valueOf("2025-02-01"), course.getStartDate(), "Start date should match");
        assertEquals(Date.valueOf("2025-12-01"), course.getEndDate(), "End date should match");
    }

    /**
     * Test retrieving a course with an invalid ID. The result should be null.
     */
    @Test
    void getCourseByInvalidId() {
        Course course = courseDao.getCourseById(-200);
        assertNull(course, "Course should be null for non-existent ID");
    }

}
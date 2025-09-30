package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseHandlerTest {

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
    }

    @Test
    void createCourse() throws JsonProcessingException {
        int courseId = CourseHandler.createCourse("test 101", "2024-01-01", "2024-12-31");

        assertNotEquals(-1, courseId);

        CourseHandler.deleteCourse(courseId);
    }

    @Test
    void createCourseNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();

        int courseId = CourseHandler.createCourse("test 101", "2024-01-01", "2024-12-31");

        assertEquals(-1, courseId);
    }

    @Test
    void updateCourse() throws JsonProcessingException {
        int courseId = CourseHandler.createCourse("test 101", "2024-01-01", "2024-12-31");

        assertEquals(1, CourseHandler.updateCourse(courseId, "test 102", "2024-01-01", "2024-12-31"));

        CourseHandler.deleteCourse(courseId);
    }

    @Test //x
    void updateCourseNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();

        int result = CourseHandler.updateCourse(1, "test 102", "2024-01-01", "2024-12-31");

        assertEquals(1, result);
    }

    @Test
    void deleteCourse() throws JsonProcessingException {
        int courseId = CourseHandler.createCourse("test 101", "2024-01-01", "2024-12-31");

        assertEquals(1, CourseHandler.deleteCourse(courseId));
    }

    @Test
    void deleteCourseNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();

        int result = CourseHandler.deleteCourse(1);

        assertEquals(-1, result);
    }

    @Test
    void getCourses() {
        assertNotNull(CourseHandler.getCourses());
    }

    @Test
    void getCoursesNotLoggedIn() {
        Account.getInstance().clearAccount();

        assertNull(CourseHandler.getCourses());
    }

    @Test
    void getCourse() {
        assertNotNull(CourseHandler.getCourse(174));
    }

    @Test //x
    void getCourseNotLoggedIn() {
        Account.getInstance().clearAccount();
        System.out.println(Account.getInstance().isLoggedIn());

        assertNotNull(CourseHandler.getCourse(174));
    }
}
package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseHandlerTest {
    static String test101String = "test 101";
    static String test102String = "test 102";
    static String startDateString = "2024-01-01";
    static String endDateString = "2024-12-31";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
    }

    @Test
    void createCourse() throws JsonProcessingException {
        final int courseId = CourseHandler.createCourse(test101String, startDateString, endDateString);

        assertNotEquals(-1, courseId);

        CourseHandler.deleteCourse(courseId);
    }

    @Test
    void createCourseNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();

        final int courseId = CourseHandler.createCourse(test101String, startDateString, endDateString);

        assertEquals(-1, courseId);
    }

    @Test
    void updateCourse() throws JsonProcessingException {
        final int courseId = CourseHandler.createCourse(test102String, startDateString, endDateString);

        assertEquals(1, CourseHandler.updateCourse(courseId, test102String, startDateString, endDateString));

        CourseHandler.deleteCourse(courseId);
    }

    @Test //x
    void updateCourseNotLoggedIn() {
        Account.getInstance().clearAccount();

        final int result = CourseHandler.updateCourse(1, test101String, startDateString, endDateString);

        assertEquals(1, result);
    }

    @Test
    void deleteCourse() throws JsonProcessingException {
        final int courseId = CourseHandler.createCourse(test101String, startDateString, endDateString);

        assertEquals(1, CourseHandler.deleteCourse(courseId));
    }

    @Test
    void deleteCourseNotLoggedIn() {
        Account.getInstance().clearAccount();

        final int result = CourseHandler.deleteCourse(1);

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

        assertNotNull(CourseHandler.getCourse(174));
    }
}
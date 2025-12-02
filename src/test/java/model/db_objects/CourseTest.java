package model.db_objects;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    static String mathematicsString = "Mathematics";

    @Test
    void testToString() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals("Course{courseId=1, studentId=101, courseName='Mathematics', startDate=1998-08-01, endDate=1998-08-01}", course.toString());
    }

    @Test
    void getCourseId() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals(1, course.getCourseId());
    }

    @Test
    void getStudentId() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals(101, course.getStudentId());
    }

    @Test
    void getCourseName() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals(mathematicsString, course.getCourseName());
    }

    @Test
    void getStartDate() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals(LocalDate.of(1998, 8, 1), course.getStartDate());
    }

    @Test
    void getEndDate() {
        Course course = new Course(1, 101, mathematicsString, LocalDate.of(1998, 8, 1), LocalDate.of(1998, 8, 1));
        assertEquals(LocalDate.of(1998, 8, 1), course.getEndDate());
    }
}
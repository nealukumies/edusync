package model.db_objects;

import model.enums.Status;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    static String titleString = "Title";
    static String descriptionString = "Description";

    @Test
    void getAssignmentId() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getAssignmentId());
    }

    @Test
    void getStudentId() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getStudentId());
    }

    @Test
    void getCourseId() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getCourseId());
    }

    @Test
    void getTitle() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(titleString, assignment.getTitle());
    }

    @Test
    void getDescription() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(descriptionString, assignment.getDescription());
    }

    @Test
    void getDeadline() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, now, Status.PENDING);
        assertEquals(now, assignment.getDeadline());
    }

    @Test
    void getStatus() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(Status.PENDING, assignment.getStatus());
    }

    @Test
    void testToString() {
        Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        String expected = "Assignment{assignmentId=1, studentId=1, courseId=1, title='Title', description='Description', deadline=" + assignment.getDeadline() + ", status=PENDING}";
        assertEquals(expected, assignment.toString());
    }
}
package model.db_objects;

import model.enums.Status;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for the Assignment class.
 */
class AssignmentTest {
    /** The title string for testing. */
    static String titleString = "Title";
    /** The description string for testing. */
    static String descriptionString = "Description";

    @Test
    void getAssignmentId() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getAssignmentId());
    }

    @Test
    void getStudentId() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getStudentId());
    }

    @Test
    void getCourseId() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(1, assignment.getCourseId());
    }

    @Test
    void getTitle() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(titleString, assignment.getTitle());
    }

    @Test
    void getDescription() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(descriptionString, assignment.getDescription());
    }

    @Test
    void getDeadline() {
        final Timestamp now = new Timestamp(System.currentTimeMillis());
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, now, Status.PENDING);
        assertEquals(now, assignment.getDeadline());
    }

    @Test
    void getStatus() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        assertEquals(Status.PENDING, assignment.getStatus());
    }

    @Test
    void testToString() {
        final Assignment assignment = new Assignment(1, 1, 1, titleString, descriptionString, new Timestamp(System.currentTimeMillis()), Status.PENDING);
        final String expected = "Assignment{assignmentId=1, studentId=1, courseId=1, title='Title', description='Description', deadline=" + assignment.getDeadline() + ", status=PENDING}";
        assertEquals(expected, assignment.toString());
    }
}
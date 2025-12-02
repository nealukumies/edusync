package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for AssignmentHandler.
 */
class AssignmentHandlerTest {
    /** String constant for email used in tests. */
    static String emailString = "katti@matikainen.fi";
    /** String constant for password used in tests. */
    static String passwordString = "salasana";
    /** String constant for assignment title used in tests. */
    static String testAssignmentTitle = "Test Assignment";
    /** String constant for deadline used in tests. */
    static String deadlineString = "2024-10-01 13:00:00";
    /** String constant for updated test content used in tests. */
    static String updatedTestString = "Updated Test";
    /** String constant for updated assignment title used in tests. */
    static String updatedAssignmentString = "Updated Assignment";
    /** String constant for email used in tests. */
    static String testString = "Test";
    /** String constant indicating assignment completion status. */
    static String completedString = "completed";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser(emailString, passwordString);
    }

    @Test
    void getAssignment() {
        assertNotNull(AssignmentHandler.getAssignment(237).body());
    }

    @Test
    void getAssignmentNotFound() {
        assertNull(AssignmentHandler.getAssignment(99999));
    }

    @Test //x
    void getAssignmentNotLoggedIn() {
        model.singletons.Account.getInstance().clearAccount();
        assertNotNull(AssignmentHandler.getAssignment(237));
    }

    @Test
    void getAssignmentsForUser() {
        assertNotNull(AssignmentHandler.getAssignmentsForUser().body());
    }

    @Test //x
    void getAssignmentsForUserNotLoggedIn() {
        model.singletons.Account.getInstance().clearAccount();
        assertNull(AssignmentHandler.getAssignmentsForUser());
    }

    @Test
    void createAssignment() throws JsonProcessingException {
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        assertTrue(assignmentId > 0);

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void createAssignmentNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        assertEquals(-1, assignmentId);
    }

    @Test
    void createAssignmentInvalidCourse() throws JsonProcessingException {
        assertEquals(-1, AssignmentHandler.createAssignment(99999, testAssignmentTitle, testString, deadlineString));
    }

    @Test
    void updateAssignment() throws JsonProcessingException {
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void updateAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.updateAssignment(99999, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));
    }

    @Test //x
    void updateAssignmentNotLoggedIn() throws JsonProcessingException {
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));

        UserHandler.loginUser(emailString, passwordString);
        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void deleteAssignment() throws JsonProcessingException {
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));
    }

    @Test
    void deleteAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.deleteAssignment(99999));
    }

    @Test //x
    void deleteAssignmentNotLoggedIn() throws JsonProcessingException {
        final int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));

        UserHandler.loginUser(emailString, passwordString);
        AssignmentHandler.deleteAssignment(assignmentId);
    }
}
package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentHandlerTest {
    static String emailString = "katti@matikainen.fi";
    static String passwordString = "salasana";
    static String testAssignmentTitle = "Test Assignment";
    static String deadlineString = "2024-10-01 13:00:00";
    static String updatedTestString = "Updated Test";
    static String updatedAssignmentString = "Updated Assignment";
    static String testString = "Test";
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
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        assertTrue(assignmentId > 0);

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void createAssignmentNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        assertEquals(-1, assignmentId);
    }

    @Test
    void createAssignmentInvalidCourse() throws JsonProcessingException {
        assertEquals(-1, AssignmentHandler.createAssignment(99999, testAssignmentTitle, testString, deadlineString));
    }

    @Test
    void updateAssignment() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void updateAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.updateAssignment(99999, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));
    }

    @Test //x
    void updateAssignmentNotLoggedIn() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, updatedAssignmentString, updatedTestString, deadlineString, completedString));

        UserHandler.loginUser(emailString, passwordString);
        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void deleteAssignment() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));
    }

    @Test
    void deleteAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.deleteAssignment(99999));
    }

    @Test //x
    void deleteAssignmentNotLoggedIn() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, testAssignmentTitle, testString, deadlineString);
        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));

        UserHandler.loginUser(emailString, passwordString);
        AssignmentHandler.deleteAssignment(assignmentId);
    }
}
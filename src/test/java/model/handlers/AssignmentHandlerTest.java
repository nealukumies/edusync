package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentHandlerTest {

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
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
        model.Singletons.Account.getInstance().clearAccount();
        assertNotNull(AssignmentHandler.getAssignment(237));
    }

    @Test
    void getAssignmentsForUser() {
        assertNotNull(AssignmentHandler.getAssignmentsForUser().body());
    }

    @Test //x
    void getAssignmentsForUserNotLoggedIn() {
        model.Singletons.Account.getInstance().clearAccount();
        assertNull(AssignmentHandler.getAssignmentsForUser());
    }

    @Test
    void createAssignment() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test","2024-10-01 13:00:00");
        assertTrue(assignmentId > 0);

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void createAssignmentNotLoggedIn() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test","2024-10-01 13:00:00");
        assertEquals(-1, assignmentId);
    }

    @Test
    void createAssignmentInvalidCourse() throws JsonProcessingException {
        assertEquals(-1, AssignmentHandler.createAssignment(99999, "Test Assignment", "Test","2024-10-01 13:00:00"));
    }

    @Test
    void updateAssignment() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test","2024-10-01 13:00:00");

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, "Updated Assignment", "Updated Test","2024-11-01 13:00:00", "completed"));

        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void updateAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.updateAssignment(99999, 174, "Updated Assignment", "Updated Test","2024-11-01 13:00:00", "completed"));
    }

    @Test //x
    void updateAssignmentNotLoggedIn() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test","2024-10-01 13:00:00");

        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.updateAssignment(assignmentId, 174, "Updated Assignment", "Updated Test","2024-11-01 13:00:00", "completed"));

        UserHandler.loginUser("katti@matikainen.fi", "salasana");
        AssignmentHandler.deleteAssignment(assignmentId);
    }

    @Test
    void deleteAssignment() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test","2024-10-01 13:00:00");

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));
    }

    @Test
    void deleteAssignmentNotFound() {
        assertEquals(-1, AssignmentHandler.deleteAssignment(99999));
    }

    @Test //x
    void deleteAssignmentNotLoggedIn() throws JsonProcessingException {
        int assignmentId = AssignmentHandler.createAssignment(174, "Test Assignment", "Test", "2024-10-01 13:00:00");
        Account.getInstance().clearAccount();

        assertEquals(1, AssignmentHandler.deleteAssignment(assignmentId));

        UserHandler.loginUser("katti@matikainen.fi", "salasana");
        AssignmentHandler.deleteAssignment(assignmentId);
    }
}
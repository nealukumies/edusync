/**
 * Unit tests for AssignmentDao class.
 */

package database;

import model.Assignment;
import model.Status;
import model.Student;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentDaoTest {
    private static AssignmentDao assignmentDao;
    private List<Integer> insertedAssignments; // To track inserted assignments for cleanup

    /**
     * Setup DAO before all tests.
     */
    @BeforeAll
    static void setup() {
        assignmentDao = new AssignmentDao();
    }

    /**
     * Initialize the inserted id list before each test.
     */
    @BeforeEach
    void init() {
        insertedAssignments = new ArrayList<>();
    }

    /**
     * Delete inserted assignments after each test.
     */
    @AfterEach
    void cleanup() {
        for (int id : insertedAssignments) {
            assignmentDao.deleteAssignment(id);
        }
        insertedAssignments.clear();
    }

    /**
     * Test inserting and deleting assignments with a valid course id. Delete the inserted assignment after testing.
     */
    @Test
    void insertAndDeleteAssignmentWithCourseTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Test Title", "Test Description", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        boolean deleted = assignmentDao.deleteAssignment(id);
        assertTrue(deleted, "Deletion successful for ID: " + id);
    }

    /**
     * Test deleting an assignment with an invalid ID.
     */
    @Test
    void deleteAssignmentFailTest() {
        boolean deleted = assignmentDao.deleteAssignment(-100);
        assertFalse(deleted, "Deletion should fail for invalid ID");
    }

    /**
     * Test inserting and deleting assignments without a course id. Delete the inserted assignment after testing.
     */
    @Test
    void insertAndDeleteAssignmentWithoutCourseTest() {
        int id = assignmentDao.insertAssignment(1, null, "Test Title No Course", "Test Description No Course", Date.valueOf("2025-11-11"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
    }

    /**
     * Test inserting and deleting assignments without a description. Delete the inserted assignment after testing.
     */
    @Test
    void insertAssignmentWithoutDescriptionTest() {
        int id = assignmentDao.insertAssignment(1, 1, "No Description", null, Date.valueOf("2025-12-12"));
        Assertions.assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
    }

    /**
     * Test inserting assignments with invalid data.
     */
    @Test
    void insertAssignmentFailTest() {
        int id = assignmentDao.insertAssignment(-200, 1, "Invalid Student", "This should fail", Date.valueOf("2025-12-12"));
        assertEquals(-1, id, "Insertion should fail for invalid student ID");
    }

    /**
     * Test inserting assignment with no title (NOT NULL in database).
     */
    @Test
    void insertAssignmentFailNoTitleTest() {
        int id = assignmentDao.insertAssignment(1, 1, null, "No title provided", Date.valueOf("2025-12-12"));
        assertEquals(-1, id, "Insertion should fail for null title");
    }

    /**
     * Test inserting assignment with no deadline (NOT NULL in database).
     */
    @Test
    void insertAssignmentFailNoDeadlineTest() {
        int id = assignmentDao.insertAssignment(1, 1, "No Deadline", "This should fail", null);
        assertEquals(-1, id, "Insertion should fail for null deadline");
    }

    /**
     * Test retrieving assignments for a valid student id.
     */
    @Test
    void getAssignmentsTest() {
        List<Assignment> assignments = assignmentDao.getAssignments(1);
        assertNotNull(assignments, "Assignments list for id 1 should not be null");
    }

    /**
     * Test retrieving assignments for an invalid student id.
     */
    @Test
    void getAssignmentsInvalidStudentTest() {
        List<Assignment> assignments = assignmentDao.getAssignments(-100);
        assertTrue(assignments.isEmpty(), "Assignments list for invalid student should be empty");
    }

    @Test
    void getAssignmentsForStudentWithNoAssignmentsTest() {
        StudentDao studentDao = new StudentDao();
        int studentId = studentDao.addStudent("NoAssign", "nojob@today.fi");
        List<Assignment> assignments = assignmentDao.getAssignments(studentId);
        assertTrue(assignments.isEmpty(), "Assignments list for student with no assignments should be empty");
        studentDao.deleteStudent(studentId);
    }

    /**
     * Test updating the status of an assignment.
     * Insert a new assignment, update its status, verify the update, and then delete the assignment.
     */
    @Test
    void setStatusTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Status Test", "Testing status update", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.setStatus(id, Status.IN_PROGRESS);
        assertTrue(updated, "Status update should be successful for ID: " + id);
        Assignment assignment = assignmentDao.getAssignmentById(id);
        Status status = assignment.getStatus();
        assertEquals(Status.IN_PROGRESS, status, "Status should be IN_PROGRESS");
    }

    /**
     * Test updating the status of an assignment with invalid data.
     * Attempt to update with a null status and an invalid assignment ID.
     */
    @Test
    void setStatusInvalidAssignmentTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Invalid Status Test", "Testing invalid status update", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.setStatus(id, null);
        assertFalse(updated, "Status update should fail for null status");
    }

    /**
     * Test updating the status of an assignment with an invalid assignment ID.
     */
    @Test
    void setStatusInvalidAssignmentIdTest() {
        boolean updated = assignmentDao.setStatus(-100, Status.COMPLETED);
        assertFalse(updated, "Status update should fail for invalid assignment ID");
    }

    /**
     * Test retrieving an assignment by its ID.
     * Insert a new assignment, retrieve it by ID, verify the details, and then delete the assignment.
     */
    @Test
    void getAssignmentByIdTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Get By ID Test", "Testing get by ID", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        Assignment assignment = assignmentDao.getAssignmentById(id);
        assertNotNull(assignment, "Assignment should not be null for valid ID");
        assertEquals("Get By ID Test", assignment.getTitle(), "Title should match");
    }

    /**
     * Test retrieving an assignment with an invalid ID.
     */
    @Test
    void getAssignmentByInvalidIdTest() {
        Assignment assignment = assignmentDao.getAssignmentById(-100);
        assertNull(assignment, "Assignment should be null for invalid ID");
    }

    @Test
    void updateAssignmentTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Update Test", "Testing update", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.updateAssignment(id, "Updated Title", "Updated Description", Date.valueOf("2025-11-11"), 1);
        assertTrue(updated, "Update should be successful for ID: " + id);
        Assignment assignment = assignmentDao.getAssignmentById(id);
        assertEquals("Updated Title", assignment.getTitle(), "Title should be updated");
        assertEquals("Updated Description", assignment.getDescription(), "Description should be updated");
        assertEquals(Date.valueOf("2025-11-11"), assignment.getDeadline(), "Deadline should be updated");
    }

    @Test
    void updateAssignmentFailTest() {
        boolean updated = assignmentDao.updateAssignment(-100, "Should Fail", "This should not work", Date.valueOf("2025-11-11"), 1);
        assertFalse(updated, "Update should fail for invalid assignment ID");
    }

    @Test
    void updateAssignmentWithNullCourseTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Update Null Course Test", "Testing update with null course", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.updateAssignment(id, "Updated Title", "Updated Description", Date.valueOf("2025-11-11"), null);
        assertTrue(updated, "Update should be successful for ID: " + id);
        Assignment assignment = assignmentDao.getAssignmentById(id);
        assertEquals("Updated Title", assignment.getTitle(), "Title should be updated");
        assertEquals("Updated Description", assignment.getDescription(), "Description should be updated");
        assertEquals(Date.valueOf("2025-11-11"), assignment.getDeadline(), "Deadline should be updated");
        assertNull(assignment.getCourseId(), "Course ID should be null after update");
    }

    @Test
    void updateAssignmentFailNoTitleTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Update No Title Test", "Testing update with no title", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.updateAssignment(id, null, "Updated Description", Date.valueOf("2025-11-11"), 1);
        assertFalse(updated, "Update should fail for null title");
    }

    @Test
    void updateAssignmentFailNoDeadlineTest() {
        int id = assignmentDao.insertAssignment(1, 1, "Update No Deadline Test", "Testing update with no deadline", Date.valueOf("2025-10-10"));
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        insertedAssignments.add(id);
        boolean updated = assignmentDao.updateAssignment(id, "Updated Title", "Updated Description", null, 1);
        assertFalse(updated, "Update should fail for null deadline");
    }
}

/**
 * Unit tests for StudentDao class.
 */
package database;

import model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {
    private static StudentDao studentDao;
    private List<Integer> insertedStudents; // To track inserted students for cleanup

    /**
     * Setup DAO before all tests.
     */
    @BeforeAll
    static void setup() {
        studentDao = new StudentDao();
    }

    /**
     * Initialize the inserted id list before each test.
     */
    @BeforeEach
    void init() {
        insertedStudents = new ArrayList<>();
    }

    /**
     * Delete inserted students after each test.
     */
    @AfterEach
    void cleanup() {
        for (int id : insertedStudents) {
            studentDao.deleteStudent(id);
        }
        insertedStudents.clear();
    }

    /**
     * Test adding and deleting a student. Deletes the inserted student after testing.
     */
    @Test
    void addAndDeleteStudentTest() {
        int id = studentDao.addStudent("Test", "test@student.fi");
        assertTrue(id > 0, "Insertion successful, got ID: " + id);
        assertTrue(studentDao.deleteStudent(id), "Deletion successful for ID: " + id);
    }

    /**
     * Test adding a student with a duplicate email. The second insertion should fail.
     */
    @Test
    void addStudentDuplicateEmailTest() {
        int id1 = studentDao.addStudent("Test1", "test@test.test");
        int id2 = studentDao.addStudent("Test2", "test@test.test");
        insertedStudents.add(id1);
        assertTrue(id1 > 0, "First insertion successful, got ID: " + id1);
        assertEquals(-1, id2, "Second insertion with duplicate email should fail");
    }

    /**
     * Test retrieving an existing student by email. The student should be found and details should match.
     */
    @Test
    void getStudentTest() {
        int student = studentDao.addStudent("Ada", "ada@hupi.fi");
        insertedStudents.add(student);
        Student fetchedStudent = studentDao.getStudent("ada@hupi.fi");
        assertNotNull(fetchedStudent, "Student should be found");
        assertEquals("Ada", fetchedStudent.getName(), "Student name should match");
    }

    /**
     * Test retrieving a non-existent student by email. The result should be null.
     */
    @Test
    void getNonExistentStudentTest() {
        Student student = studentDao.getStudent("nosuch@email.com");
        assertNull(student, "Student should not be found");
    }

    /**
     * Test deleting a non-existent student. The deletion should fail.
     */
    @Test
    void deleteNonExistentStudentTest() {
        boolean result = studentDao.deleteStudent(-200);
        assertFalse(result, "Deletion should fail for non-existent student ID");
    }
}
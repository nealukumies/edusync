package model.db_objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for Student. */
class StudentTest {
    /** The name string used for testing. */
    static String nameString = "John Doe";
    /** The email string used for testing. */
    static String emailString = "John.Doe@gmail.com";

    @Test
    void testToString() {
        final Student student = new Student(1, nameString, emailString);
        assertEquals("Student id=1, name='John Doe', email='John.Doe@gmail.com'}", student.toString());
    }

    @Test
    void getId() {
        final Student student = new Student(1, nameString, emailString);
        assertEquals(1, student.getId());
    }

    @Test
    void getName() {
        final Student student = new Student(1, nameString, emailString);
        assertEquals(nameString, student.getName());
    }

    @Test
    void getEmail() {
        final Student student = new Student(1, nameString, emailString);
        assertEquals(emailString, student.getEmail());
    }
}
package model.db_objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    static String nameString = "John Doe";
    static String emailString = "John.Doe@gmail.com";

    @Test
    void testToString() {
        Student student = new Student(1, nameString, emailString);
        assertEquals("Student id=1, name='John Doe', email='John.Doe@gmail.com'}", student.toString());
    }

    @Test
    void getId() {
        Student student = new Student(1, nameString, emailString);
        assertEquals(1, student.getId());
    }

    @Test
    void getName() {
        Student student = new Student(1, nameString, emailString);
        assertEquals(nameString, student.getName());
    }

    @Test
    void getEmail() {
        Student student = new Student(1, nameString, emailString);
        assertEquals(emailString, student.getEmail());
    }
}
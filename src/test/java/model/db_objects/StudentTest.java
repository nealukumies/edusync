package model.db_objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testToString() {
        Student student = new Student(1, "John Doe", "John.Doe@gmail.com");
        assertEquals("Student id=1, name='John Doe', email='John.Doe@gmail.com'}", student.toString());
    }

    @Test
    void getId() {
        Student student = new Student(1, "John Doe", "John.Doe@gmail.com");
        assertEquals(1, student.getId());
    }

    @Test
    void getName() {
        Student student = new Student(1, "John Doe", "John.Doe@gmail.com");
        assertEquals("John Doe", student.getName());
    }

    @Test
    void getEmail() {
        Student student = new Student(1, "John Doe", "John.Doe@gmail.com");
        assertEquals("John.Doe@gmail.com", student.getEmail());
    }
}
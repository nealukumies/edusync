package model;

import database.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private StudentDao dao;

    @BeforeEach
    void setUp() {
        dao = new StudentDao();
    }

    @Test
    void TryWorkingEmailToLogin() {
        Student student = dao.getStudent("Katti.Matikainen@katti.org");
        assertEquals(32, student.getId());
    }

    @Test
    void TryInvalidEmailToLogin() {
        Student student = dao.getStudent("Matti.Katikainen@katti.org");
        assertNull(student);
    }
}
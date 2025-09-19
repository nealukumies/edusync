package model;

import database.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Login.tryLogin;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void TryWorkingEmailToLogin() {
        assertEquals(32, tryLogin("Katti.Matikainen@katti.org"));
    }

    @Test
    void TryInvalidEmailToLogin() {
        assertEquals(-1, tryLogin("Matti.Katikainen@katti.org"));
    }
}
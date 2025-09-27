package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
    }

    @Test
    void loginExistingUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(171, UserHandler.loginUser("katti@matikainen.fi", "salasana"));
    }

    @Test
    void loginExistingUserWrongPassword() throws JsonProcessingException {
        assertEquals(-1, UserHandler.loginUser("katti@matikainen.fi", "sanasala"));
    }

    @Test
    void loginNonExistingUser() throws JsonProcessingException {
        assertEquals(-1, UserHandler.loginUser("matti.mahtava@gmail.org", "password"));
    }

    @Test
    void getUserLoggedIn() {
        assertNotNull(UserHandler.getUser().body());
    }

    @Test
    void getUserNotLoggedIn() {
        Account.getInstance().clearAccount();
        System.out.println(1);
        assertNull(UserHandler.getUser());
    }

    @Test
    void updateUser() throws JsonProcessingException {
        assertEquals(1, UserHandler.updateUser("Katti", "Katti123@matikainen.fi"));
        UserHandler.updateUser("katti", "katti@matikainen.fi");
    }

    @Test
    void registerUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(1, UserHandler.registerUser("test", "test@test.fi", "salasana"));
        UserHandler.deleteUser();
    }

    @Test
    void deleteUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        UserHandler.registerUser("test", "test@test.fi", "salasana");
        assertEquals(1, UserHandler.deleteUser());
    }
}
package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

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
    void logoutExistingUser() throws JsonProcessingException {
        UserHandler.logoutUser();
        assertEquals("Not logged in", Account.getInstance().getEmail());
    }

    @Test
    void loadAccountNoFile() {
        UserHandler.logoutUser();
        UserHandler.loadAccount();
        assertEquals("Not logged in", Account.getInstance().getEmail());
    }

    @Test
    void loadAccountFromFile() throws JsonProcessingException {
        UserHandler.logoutUser();
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
        Account.getInstance().clearAccount();
        UserHandler.loadAccount();
        assertEquals("katti@matikainen.fi", Account.getInstance().getEmail());
    }

    @Test
    void removeAccountFile() throws JsonProcessingException {
        UserHandler.logoutUser();
        File f = new File("account.txt");
        assertFalse(f.exists());
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

    @Test //x
    void registerUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(-1, UserHandler.registerUser("test", "test2@test.fi", "salasana"));
        UserHandler.deleteUser();
    }

    @Test //x
    void deleteUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        UserHandler.registerUser("test", "test@test.fi", "salasana");
        assertEquals(-1, UserHandler.deleteUser());
    }
}
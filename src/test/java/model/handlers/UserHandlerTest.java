package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.singletons.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {
    static String emailString = "katti@matikainen.fi";
    static String passwordString = "salasana";
    static String notLoggedInString = "Not logged in";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        UserHandler.loginUser(emailString, passwordString);
    }

    @Test
    void loginExistingUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(171, UserHandler.loginUser(emailString, passwordString));
    }

    @Test
    void logoutExistingUser() {
        UserHandler.logoutUser();
        assertEquals(notLoggedInString, Account.getInstance().getEmail());
    }

    @Test
    void loadAccountNoFile() {
        UserHandler.logoutUser();
        UserHandler.loadAccount();
        assertEquals(notLoggedInString, Account.getInstance().getEmail());
    }

    @Test
    void loadAccountFromFile() throws JsonProcessingException {
        UserHandler.logoutUser();
        UserHandler.loginUser(emailString, passwordString);
        Account.getInstance().clearAccount();
        UserHandler.loadAccount();
        assertEquals(emailString, Account.getInstance().getEmail());
    }

    @Test
    void removeAccountFile() {
        UserHandler.logoutUser();
        File f = new File("account.txt");
        assertFalse(f.exists());
    }

    @Test
    void loginExistingUserWrongPassword() throws JsonProcessingException {
        assertEquals(-1, UserHandler.loginUser(emailString, "sanasala"));
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
        assertNull(UserHandler.getUser());
    }

    @Test
    void updateUser() throws JsonProcessingException {
        assertEquals(1, UserHandler.updateUser("Katti", "Katti123@matikainen.fi"));
        UserHandler.updateUser("katti", emailString);
    }

    @Test //x
    void registerUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        assertEquals(-1, UserHandler.registerUser("test", "test2@test.fi", passwordString));
        UserHandler.deleteUser();
    }

    @Test //x
    void deleteUser() throws JsonProcessingException {
        Account.getInstance().clearAccount();
        UserHandler.registerUser("test", "test@test.fi", passwordString);
        assertEquals(-1, UserHandler.deleteUser());
    }
}
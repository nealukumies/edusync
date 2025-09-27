package model.Singletons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void getInstance() {
        assertNotNull(Account.getInstance());
        assertSame(Account.getInstance(), Account.getInstance());
    }

    @Test
    void isLoggedInIfNotLoggedIn() {
        Account account = Account.getInstance();
        assertFalse(account.isLoggedIn());
    }

    @Test
    void isLoggedInIfLoggedIn() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertTrue(account.isLoggedIn());
    }

    @Test
    void clearAccount() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        account.clearAccount();
        assertFalse(account.isLoggedIn());
    }

    @Test
    void setAccountDetailsIfNotLoggedIn() {
        Account account = Account.getInstance();
        account.clearAccount();
        assertTrue(account.setAccountDetails(1, "Test User", "katti@matikainen", "user"));
    }

    @Test
    void setAccountDetailsIfLoggedIn() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertFalse(account.setAccountDetails(2, "Another User", "another@user", "admin"));
    }

        @Test
    void getStudentId() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertEquals(1, account.getStudentId());
    }

    @Test
    void getName() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertEquals("Test User", account.getName());
    }

    @Test
    void getEmail() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertEquals("katti@matikainen", account.getEmail());
    }

    @Test
    void getRole() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, "Test User", "katti@matikainen", "user");
        assertEquals("user", account.getRole());
    }
}
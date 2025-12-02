package model.singletons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    static String nameString = "Test User";
    static String emailString = "katti@matikainen";
    static String roleString = "user";

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
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertTrue(account.isLoggedIn());
    }

    @Test
    void clearAccount() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        account.clearAccount();
        assertFalse(account.isLoggedIn());
    }

    @Test
    void setAccountDetailsIfNotLoggedIn() {
        Account account = Account.getInstance();
        account.clearAccount();
        assertTrue(account.setAccountDetails(1, nameString, emailString, roleString));
    }

    @Test
    void setAccountDetailsIfLoggedIn() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertFalse(account.setAccountDetails(2, "Another User", "another@user", "admin"));
    }

        @Test
    void getStudentId() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(1, account.getStudentId());
    }

    @Test
    void getName() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(nameString, account.getName());
    }

    @Test
    void getEmail() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(emailString, account.getEmail());
    }

    @Test
    void getRole() {
        Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(roleString, account.getRole());
    }
}
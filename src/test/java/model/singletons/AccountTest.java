package model.singletons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Test class for the Account singleton. */
class AccountTest {
    /** The name of the user. */
    static String nameString = "Test User";
    /** The email of the user. */
    static String emailString = "katti@matikainen";
    /** The role of the user in the account. */
    static String roleString = "user";

    @Test
    void getInstance() {
        assertNotNull(Account.getInstance());
        assertSame(Account.getInstance(), Account.getInstance());
    }

    @Test
    void isLoggedInIfNotLoggedIn() {
        final Account account = Account.getInstance();
        assertFalse(account.isLoggedIn());
    }

    @Test
    void isLoggedInIfLoggedIn() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertTrue(account.isLoggedIn());
    }

    @Test
    void clearAccount() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        account.clearAccount();
        assertFalse(account.isLoggedIn());
    }

    @Test
    void setAccountDetailsIfNotLoggedIn() {
        final Account account = Account.getInstance();
        account.clearAccount();
        assertTrue(account.setAccountDetails(1, nameString, emailString, roleString));
    }

    @Test
    void setAccountDetailsIfLoggedIn() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertFalse(account.setAccountDetails(2, "Another User", "another@user", "admin"));
    }

        @Test
    void getStudentId() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(1, account.getStudentId());
    }

    @Test
    void getName() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(nameString, account.getName());
    }

    @Test
    void getEmail() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(emailString, account.getEmail());
    }

    @Test
    void getRole() {
        final Account account = Account.getInstance();
        account.setAccountDetails(1, nameString, emailString, roleString);
        assertEquals(roleString, account.getRole());
    }
}
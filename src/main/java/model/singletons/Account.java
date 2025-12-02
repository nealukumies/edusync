package model.singletons;

import java.io.Serializable;

/** * Singleton class to manage user account details.
 */
public class Account implements Serializable {
    /** The singleton instance of the Account class */
    private static Account instance;

    /** The student ID of the user */
    private int studentId;
    /** The name of the user */
    private String name;
    /** The email of the user */
    private String email;
    /** The role of the user (e.g., student, admin) */
    private String role;

    /** Text to display when no user is logged in */
    static final String NOT_LOGGED_IN_TXT = "Not logged in";

    private Account() {
    }

    public static Account getInstance() {
        if (instance == null) {
            instance = new Account();
        }
        return instance;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return this.name != null;
    }

    /**
     * Clears the account details, effectively logging out the user.
     */
    public void clearAccount() {
        this.studentId = 0;
        this.name = null;
        this.email = null;
        this.role = null;
    }

    /**
     * Sets the account details if not already logged in.
     *
     * @param studentId The student ID
     * @param name      The name of the user
     * @param email     The email of the user
     * @param role      The role of the user
     * @return true if account details were set, false if already logged in
     */
    public boolean setAccountDetails(final int studentId, final String name, final String email, final String role) {
        if (!isLoggedIn()) {
            this.studentId = studentId;
            this.name = name;
            this.email = email;
            this.role = role;

            return true;
        } else {
            return false;
        }
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        if (name == null) {
            return NOT_LOGGED_IN_TXT;
        }
        return name;
    }

    public String getEmail() {
        if (email == null) {
            return NOT_LOGGED_IN_TXT;
        }
        return email;
    }

    public String getRole() {
        if (role == null) {
            return NOT_LOGGED_IN_TXT;
        }
        return role;
    }
}

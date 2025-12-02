package model.singletons;

import java.io.Serializable;

public class Account implements Serializable {
    private static Account instance;

    private int studentId;
    private String name;
    private String email;
    private String role;

    static final String NOT_LOGGED_IN_TXT = "Not logged in";

    private Account() {
    }

    public static Account getInstance() {
        if (instance == null) {
            instance = new Account();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return this.name != null;
    }

    public void clearAccount() {
        this.studentId = 0;
        this.name = null;
        this.email = null;
        this.role = null;
    }

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

package model.Singletons;

import java.io.Serializable;

public class Account implements Serializable {
    private static Account INSTANCE;

    private int studentId;
    private String name;
    private String email;
    private String role;

    private final String NOT_LOGGED_IN_TEXT = "Not logged in";

    private Account() {
    }

    public static Account getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Account();
        }
        return INSTANCE;
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

    public boolean setAccountDetails(int studentId, String name, String email, String role) {
        if (!isLoggedIn()) {
            this.studentId = studentId;
            this.name = name;
            this.email = email;
            this.role = role;

            return true;
        } else {
            System.out.println("Account already logged in! Logout first!");

            return false;
        }
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        if (name == null) {
            return NOT_LOGGED_IN_TEXT;
        }
        return name;
    }

    public String getEmail() {
        if (email == null) {
            return NOT_LOGGED_IN_TEXT;
        }
        return email;
    }

    public String getRole() {
        if (role == null) {
            return NOT_LOGGED_IN_TEXT;
        }
        return role;
    }
}

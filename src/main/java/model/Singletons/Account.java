package model.Singletons;

import java.io.Serializable;

public class Account implements Serializable {
    private static Account instance;

    private int studentId;
    private String name;
    private String email;
    private String role;

    private final static String notLoggedInTxt = "Not logged in";

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

    public boolean setAccountDetails(int studentId, String name, String email, String role) {
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
            return notLoggedInTxt;
        }
        return name;
    }

    public String getEmail() {
        if (email == null) {
            return notLoggedInTxt;
        }
        return email;
    }

    public String getRole() {
        if (role == null) {
            return notLoggedInTxt;
        }
        return role;
    }
}

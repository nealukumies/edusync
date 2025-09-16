package model;

import database.StudentDao;

public class Login {
    static public int tryLogin(String email) {
        StudentDao dao = new StudentDao();

        Student student = dao.getStudent(email);

        if (student != null) {
            return student.getId();
        } else {
            return -1;
        }
    }
}

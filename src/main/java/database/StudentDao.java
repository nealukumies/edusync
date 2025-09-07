package database;

import model.Student;

import java.sql.Connection;
import java.sql.*;

public class StudentDao {

    // Method to add a new student to the database.
    public void addStudent(String name, String email) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO students (name, email) VALUES (?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding student.");
            e.printStackTrace();
        }
    }

    // Method to retrieve a student by email. Returns Student object if found, null otherwise.
    public Student getStudent(String email) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT student_id, name FROM students WHERE email = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                return new Student(id, name, email);
            } else {
                System.out.println("No student found with the given email.");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving student ID.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();
        //studentDao.addStudent("John", "john@gmail.com");
        System.out.println("With john@gmail.com found student: " + studentDao.getStudent("john@gmail.com"));

    }
}

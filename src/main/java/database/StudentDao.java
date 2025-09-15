/**
 * Data Access Object (DAO) class for managing student records in the database.
 * Provides methods to add a new student and retrieve a student by email.
 */

package database;

import model.Student;

import java.sql.Connection;
import java.sql.*;

public class StudentDao {

    /**
     * Adds a new student to the database. Returns the generated student ID, or -1 if insertion fails.
     * @param name
     * @param email
     */
    public int addStudent(String name, String email) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("Error: Name and email cannot be null or empty.");
            return -1;
        }
        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO students (name, email) VALUES (?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated student ID
                }
            } return -1; // Indicate failure
        } catch (SQLException e) {
            System.out.println("Error adding student.");
            return -1; // Indicate failure
        }
    }

    /**
     * Retrieves a student by email. Returns a Student object if found, or null if not found or an error occurs.
     * @param email
     * @return
     */
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
            }
            return null; // Student not found
        } catch (SQLException e) {
            System.out.println("Error retrieving student ID.");
            return null;
        }
    }

    /**
     * Retrieves a student by student ID. Returns a Student object if found, or null if not found or an error occurs.
     * @param studentId
     * @return
     */

    public Student getStudentById(int studentId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT name, email FROM students WHERE student_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                return new Student(studentId, name, email);
            }
            return null; // Student not found
        } catch (SQLException e) {
            System.out.println("Error retrieving student.");
            return null;
        }
    }

    /**
     * Deletes a student by student ID. Returns true if deletion was successful, false otherwise.
     * @param studentId
     * @return
     */
    public boolean deleteStudent(int studentId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "DELETE FROM students WHERE student_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting student.");
            return false;
        }
    }

    public boolean updateStudentName(int studentId, String newName) {
        if (newName == null || newName.isEmpty()) {
            System.out.println("Error: Name cannot be null or empty.");
            return false;
        }
        Connection conn = MariaDBConnection.getConnection();
        String sql = "UPDATE students SET name = ? WHERE student_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, studentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating student name.");
            return false;
        }
    }

    public boolean updateStudentEmail(int studentId, String newEmail) {
        if (newEmail == null || newEmail.isEmpty()) {
            System.out.println("Error: Email cannot be null or empty.");
            return false;
        }
        Connection conn = MariaDBConnection.getConnection();
        String sql = "UPDATE students SET email = ? WHERE student_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newEmail);
            ps.setInt(2, studentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating student email.");
            return false;
        }
    }

//    // Simple main method for manual testing
//    public static void main(String[] args) {
//        StudentDao studentDao = new StudentDao();
//        //studentDao.addStudent("John", "john@gmail.com");
//        System.out.println("With john@gmail.com found student: " + studentDao.getStudent("john@gmail.com"));
//        int id = studentDao.addStudent("Jane", "jane@mail.com");
//        System.out.println("For id: " + id + " found student: " + studentDao.getStudentById(id) );
//        studentDao.updateStudentName(id, "Jane Doe");
//        System.out.println("After update, found student: " + studentDao.getStudentById(id) );
//    }
}

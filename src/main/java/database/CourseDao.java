package database;

import model.Course;

import java.sql.Connection;
import java.sql.*;

public class CourseDao {

    public void addCourse(int studentId, String courseName, Date startDate, Date endDate) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO courses (student_id, course_name, start_date, end_date) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setString(2, courseName);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding student.");
            e.printStackTrace();
        }
    }

    public Course getCourseById(int courseId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT * FROM courses WHERE course_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("course_id"),
                    rs.getInt("student_id"),
                    rs.getString("course_name"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving course.");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CourseDao dao = new CourseDao();
        //dao.addCourse(1, "Mathematics", Date.valueOf("2024-09-01"), Date.valueOf("2025-06-30"));
        System.out.println("Course with id 1: " + dao.getCourseById(1));

    }
}

/**
 * Data Access Object for Course entity. Provides methods to add and retrieve courses from the database.
 */
package database;

import model.Course;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class CourseDao {

    /**
     * Adds a new course to the database. Returns the generated course ID, or -1 if insertion fails.
     * @param studentId
     * @param courseName
     * @param startDate
     * @param endDate
     */
    public int addCourse(int studentId, String courseName, Date startDate, Date endDate) {
        if (startDate != null && endDate != null && endDate.before(startDate)) {
            System.out.println("Error: End date cannot be before start date.");
            return -1;
        }
        if (courseName == null || courseName.isEmpty()) {
            System.out.println("Error: Course name cannot be null or empty.");
            return -1;
        }
        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO courses (student_id, course_name, start_date, end_date) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, studentId);
            ps.setString(2, courseName);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated course ID
                }
            } return -1; // Indicate failure

        } catch (SQLException e) {
            System.out.println("Error adding course.");
            return -1; // Indicate failure
        }
    }

    /**
     * Retrieves a course by its ID. Returns a Course object if found, or null if not found or an error occurs.
     * @param courseId
     * @return
     */
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
            return null;
        } catch (SQLException e) {
            System.out.println("Error retrieving course.");
            return null;
        }
    }

    public ArrayList<Course> getAllCourses(int studentId) {
        ArrayList<Course> courses = new ArrayList<>();
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT * FROM courses WHERE student_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                studentId = rs.getInt("student_id");
                String courseName = rs.getString("course_name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                courses.add(new Course(courseId, studentId, courseName, startDate, endDate));
            }
            return courses;
        } catch (SQLException e) {
            System.out.println("Error retrieving courses.");
            return null;
        }
    }

    /**
     * Deletes a course by its ID. Also deletes associated schedules.
     * Returns true if deletion was successful, false otherwise.
     * @param courseId
     * @return boolean
     */
    public boolean deleteCourse(int courseId) {
        Connection conn = MariaDBConnection.getConnection();
        try {
            PreparedStatement psSchedules = conn.prepareStatement("DELETE FROM schedule WHERE course_id = ?;");
            psSchedules.setInt(1, courseId);
            psSchedules.executeUpdate();
            PreparedStatement psCourse = conn.prepareStatement("DELETE FROM courses WHERE course_id = ?;");
            psCourse.setInt(1, courseId);
            int rows = psCourse.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting course.");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        CourseDao dao = new CourseDao();
        //dao.addCourse(1, "Mathematics", Date.valueOf("2024-09-01"), Date.valueOf("2025-06-30"));
        //System.out.println("Course with id 1: " + dao.getCourseById(1));
        //dao.addCourse(1, "History", Date.valueOf("2025-09-01"), Date.valueOf("2025-12-30"));
        //dao.addCourse(1, "Biology", Date.valueOf("2025-10-01"), Date.valueOf("2026-01-30"));
        System.out.println("All courses for student with id 1: " + dao.getAllCourses(1));

    }
}

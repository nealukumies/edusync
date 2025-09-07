package database;

import model.Assignment;
import model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDao {

    public void addAssignment(int studentId, Integer courseId, String title, String description, Date deadline) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO assignments (student_id, course_id, title, description, deadline) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            if (courseId != null) {
                ps.setInt(2, courseId);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setDate(5, deadline);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Assignment added successfully.");
            } else {
                System.out.println("Failed to add assignment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding assignment: " + e.getMessage());
        }
    }

    public List<Assignment> getAssignments(int studentId) {
        List<Assignment> assignments = new ArrayList<>();
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT * FROM assignments WHERE student_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { // loop over all rows
                int id = rs.getInt("assignment_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Date deadline = rs.getDate("deadline");
                Integer courseId = rs.getObject("course_id") != null ? rs.getInt("course_id") : null;
                Status status = Status.fromDbValue(rs.getString("status"));
                assignments.add(new Assignment(id, studentId, courseId, title, description, deadline, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving assignments: " + e.getMessage());
            return null;
        }
        return assignments;
    }

    public void setStatus(int assignmentId, Status status){
        Connection conn = MariaDBConnection.getConnection();
        String sql = "UPDATE assignments SET status = ? WHERE assignment_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.getDbValue());
            ps.setInt(2, assignmentId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Assignment status updated successfully.");
            } else {
                System.out.println("Failed to update assignment status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating assignment status: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AssignmentDao dao = new AssignmentDao();
        //dao.addAssignment(1, 1, "Math Homework", "Complete exercises 1-10", Date.valueOf("2025-10-11"));
        //dao.setStatus(2, Status.IN_PROGRESS);
        List<Assignment> assignments = dao.getAssignments(1);
        for (Assignment a : assignments) {
            System.out.println("ID: " + a.getAssignmentId() + ", Title: " + a.getTitle() + ", Status: " + a.getStatus());
        }
    }
}

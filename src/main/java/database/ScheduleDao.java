/**
 * Data Access Object (DAO) for managing schedules in the database.
 */
package database;

import model.Schedule;
import model.Weekday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static java.sql.Time.valueOf;

public class ScheduleDao {

    /**
     * Inserts a new schedule into the schedules table. Returns the generated schedule ID, or -1 if insertion fails.
     * Returns 0 if no rows were affected.
     *
     * @param courseId
     * @param weekday
     * @param startTime
     * @param endTime
     * @return
     */
    public int insertSchedule(Integer courseId, Weekday weekday, LocalTime startTime, LocalTime endTime) {
        if (weekday == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Course ID, weekday, start time, and end time must not be null");
        }

        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO schedule (course_id, weekday, start_time, end_time) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (courseId != null) {
                ps.setInt(1, courseId);
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, weekday.name());
            ps.setTime(3, valueOf(startTime));
            ps.setTime(4, valueOf(endTime));
            int rows = ps.executeUpdate();
            if (rows > 0) {
                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated schedule ID
                }
            }
            return 0; // Indicate no rows affected
        } catch (SQLException e) {
            System.out.println("Error inserting schedule: " + e.getMessage());
            return -1; // Indicate failure
        }
    }

    public boolean deleteSchedule(int scheduleId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "DELETE FROM schedule WHERE schedule_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleId);
            int rows = ps.executeUpdate();
            return rows > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            System.out.println("Error deleting schedule: " + e.getMessage());
            return false; // Indicate failure
        }
    }

    public Schedule getSchedule(int scheduleId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int courseId = rs.getInt("course_id");
                Weekday weekday = Weekday.valueOf(rs.getString("weekday").toUpperCase());
                LocalTime startTime = rs.getTime("start_time").toLocalTime();
                LocalTime endTime = rs.getTime("end_time").toLocalTime();
                return new Schedule(scheduleId, courseId, weekday, startTime, endTime);
            }
            return null; // Schedule not found
        } catch (SQLException e) {
            System.out.println("Error retrieving schedule: " + e.getMessage());
            return null; // Indicate failure
        }
    }

    // For testing purposes
//    public static void main(String[] args) {
//        ScheduleDao dao = new ScheduleDao();
//        int newId = dao.insertSchedule(1, Weekday.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));
//        System.out.println("Inserted schedule ID: " + newId);
//
//        Schedule schedule = dao.getSchedule(newId);
//        System.out.println("Retrieved schedule: " + schedule);
//
//        boolean deleted = dao.deleteSchedule(newId);
//        System.out.println("Deleted schedule: " + deleted);
//    }
}

/**
 * Data Access Object (DAO) for managing schedules in the database.
 */
package database;

import model.Schedule;
import model.Weekday;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

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
     * @return int - schedule ID, 0 if no rows affected, -1 if error occurs
     */
    public int insertSchedule(Integer courseId, Weekday weekday, LocalTime startTime, LocalTime endTime) {
        if (courseId == null || weekday == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Course ID, weekday, start time, and end time must not be null");
        }

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Connection conn = MariaDBConnection.getConnection();
        String sql = "INSERT INTO schedule (course_id, weekday, start_time, end_time) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, courseId);
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

    /**
     * Deletes a schedule by its ID. Returns true if deletion was successful, false otherwise.
     * @param scheduleId
     * @return boolean
     */
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

    /**
     * Retrieves a schedule by its ID. Returns a Schedule object if found, or null if not found or an error occurs.
     * @param scheduleId
     * @return Schedule
     */
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

    /**
     * Retrieves all schedules for a given course ID. Returns a list of Schedule objects, or an empty list if none found or an error occurs.
     * @param courseId
     * @return ArrayList<Schedule>
     */
    public ArrayList<Schedule> getAllSchedulesForCourse(int courseId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT * FROM schedule WHERE course_id = ?;";
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                courseId = rs.getInt("course_id");
                Weekday weekday = Weekday.valueOf(rs.getString("weekday").toUpperCase());
                LocalTime startTime = rs.getTime("start_time").toLocalTime();
                LocalTime endTime = rs.getTime("end_time").toLocalTime();
                schedules.add(new Schedule(scheduleId, courseId, weekday, startTime, endTime));
            }
            return schedules;
        } catch (SQLException e) {
            System.out.println("Error retrieving schedules: " + e.getMessage());
            return schedules; // Return empty list on failure
        }
    }

    /**
     * Retrieves all schedules for all courses of a given student ID.
     * Returns a list of Schedule objects, or an empty list if none found or an error occurs.
     * @param studentId
     * @return ArrayList<Schedule>
     */

    public ArrayList<Schedule> getAllSchedulesForStudent(int studentId) {
        Connection conn = MariaDBConnection.getConnection();
        String sql = "SELECT s.schedule_id, s.course_id, s.weekday, s.start_time, s.end_time " +
                     "FROM schedule s " +
                     "JOIN courses c ON s.course_id = c.course_id " +
                     "WHERE c.student_id = ?;";
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                int courseId = rs.getInt("course_id");
                Weekday weekday = Weekday.valueOf(rs.getString("weekday").toUpperCase());
                LocalTime startTime = rs.getTime("start_time").toLocalTime();
                LocalTime endTime = rs.getTime("end_time").toLocalTime();
                schedules.add(new Schedule(scheduleId, courseId, weekday, startTime, endTime));
            }
            return schedules;
        } catch (SQLException e) {
            System.out.println("Error retrieving schedules for student: " + e.getMessage());
            return schedules; // Return empty list on failure
        }
    }

    //For testing purposes
    public static void main(String[] args) {
        ScheduleDao dao = new ScheduleDao();
//        int newId = dao.insertSchedule(1, Weekday.THURSDAY, LocalTime.of(12, 0), LocalTime.of(15, 30));
//        System.out.println("Inserted schedule ID: " + newId);
//
//        Schedule schedule = dao.getSchedule(newId);
//        System.out.println("Retrieved schedule: " + schedule);

        //dao.insertSchedule(null, Weekday.TUESDAY, LocalTime.of(9, 0), LocalTime.of(10, 30));

        ArrayList<Schedule> schedules = dao.getAllSchedulesForStudent(1);
        System.out.println("All schedules for student 1: " + schedules);

//        boolean deleted = dao.deleteSchedule(newId);
//        System.out.println("Deleted schedule: " + deleted);
    }
}

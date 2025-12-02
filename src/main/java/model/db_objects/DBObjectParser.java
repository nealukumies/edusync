package model.db_objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.Status;
import model.enums.Weekday;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Utility class for parsing database objects from HTTP responses.
 */
public class DBObjectParser {
    /** Course ID field name.
     */
    private static final String COURSE_ID = "courseId";
    /** Student ID field name.
     */
    private static final String STUDENT_ID = "studentId";
    /** Time format string for parsing time values.
     */
    private static final String TIME_STRING = "HH:mm";

    private DBObjectParser () {}

    /**
     * Parses a single course from the given HTTP response.
     *
     * @param data The HTTP response containing the course data in JSON format.
     * @return A Course object parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static Student parseStudent(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return null;
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());

        return new Student(
                jsonNode.get("id").asInt(),
                jsonNode.get("name").asText(),
                jsonNode.get("email").asText()
        );
    }

    /** Parses a single course from the given HTTP response.
     *
     * @param data The HTTP response containing the course data in JSON format.
     * @return A Course object parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static Course parseCourse(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return null;
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());

        return new Course(
                jsonNode.get(COURSE_ID).asInt(),
                jsonNode.get(STUDENT_ID).asInt(),
                jsonNode.get("courseName").asText(),
                LocalDate.parse(jsonNode.get("startDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(jsonNode.get("endDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    /**
     * Parses a list of courses from the given HTTP response.
     *
     * @param data The HTTP response containing the course data in JSON format.
     * @return A list of Course objects parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Course> parseCourseList(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return new ArrayList<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());
        final ArrayList<Course> courses = new ArrayList<>();

        if (jsonNode.isArray()) {
            for (final JsonNode node : jsonNode) {
                courses.add(new Course(
                        node.get(COURSE_ID).asInt(),
                        node.get(STUDENT_ID).asInt(),
                        node.get("courseName").asText(),
                        LocalDate.parse(node.get("startDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE),
                        LocalDate.parse(node.get("endDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE)
                ));
            }
        }
        return courses;
    }

    /**
     * Parses a single schedule from the given HTTP response.
     *
     * @param data The HTTP response containing the schedule data in JSON format.
     * @return A Schedule object parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static Schedule parseSchedule(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return null;
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());

        return new Schedule(
                jsonNode.get("scheduleId").asInt(),
                jsonNode.get(COURSE_ID).asInt(),
                Weekday.valueOf(jsonNode.get("weekday").asText()),
                LocalTime.parse(jsonNode.get("startTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING)),
                LocalTime.parse(jsonNode.get("endTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING))
        );
    }

    /**
     * Parses a list of schedules from the given HTTP response.
     *
     * @param data The HTTP response containing the schedule data in JSON format.
     * @return A list of Schedule objects parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Schedule> parseScheduleList(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return new ArrayList<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());
        final ArrayList<Schedule> schedules = new ArrayList<>();

        if (jsonNode.isArray()) {
            for (final JsonNode node : jsonNode) {
                schedules.add(new Schedule(
                        node.get("scheduleId").asInt(),
                        node.get(COURSE_ID).asInt(),
                        Weekday.valueOf(node.get("weekday").asText()),
                        LocalTime.parse(node.get("startTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING)),
                        LocalTime.parse(node.get("endTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING))
                ));
            }
        }
        return schedules;
    }

    /**
     * Parses a single assignment from the given HTTP response.
     *
     * @param data The HTTP response containing the assignment data in JSON format.
     * @return An Assignment object parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static Assignment parseAssignment(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return null;
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());

        return new Assignment(
                jsonNode.get("assignmentId").asInt(),
                jsonNode.get(STUDENT_ID).asInt(),
                jsonNode.get(COURSE_ID).asInt(),
                jsonNode.get("title").asText(),
                jsonNode.get("description").asText(),
                Timestamp.valueOf(jsonNode.get("deadline").asText()),
                Status.valueOf(jsonNode.get("status").asText())
        );
    }

    /**
     * Parses a list of assignments from the given HTTP response.
     *
     * @param data The HTTP response containing the assignment data in JSON format.
     * @return A list of Assignment objects parsed from the response.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Assignment> parseAssignmentList(final HttpResponse<String> data) throws JsonProcessingException {
        if (data == null || data.body() == null || data.body().isEmpty() || data.statusCode() != 200) {
            return new ArrayList<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(data.body());
        final ArrayList<Assignment> assignments = new ArrayList<>();


        if (jsonNode.isArray()) {
            for (final JsonNode node : jsonNode) {

                assignments.add(new Assignment(
                        node.get("assignmentId").asInt(),
                        node.get(STUDENT_ID).asInt(),
                        node.get(COURSE_ID).asInt(),
                        node.get("title").asText(),
                        node.get("description").asText(),
                        Timestamp.valueOf(node.get("deadline").asText()),
                        Status.valueOf(node.get("status").asText())
                ));
            }
        }
        return assignments;
    }
}

package model.DBObjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Enums.Status;
import model.Enums.Weekday;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBObjectParser {
    private static final String COURSE_ID = "courseId";
    private static final String STUDENT_ID = "studentId";
    private static final String TIME_STRING = "HH:mm";

    public static Student parseStudent(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());

        return new Student(
                jsonNode.get("id").asInt(),
                jsonNode.get("name").asText(),
                jsonNode.get("email").asText()
        );
    }

    public static Course parseCourse(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());

        return new Course(
                jsonNode.get(COURSE_ID).asInt(),
                jsonNode.get(STUDENT_ID).asInt(),
                jsonNode.get("courseName").asText(),
                LocalDate.parse(jsonNode.get("startDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(jsonNode.get("endDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

    public static ArrayList<Course> parseCourseList(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());
        ArrayList<Course> courses = new ArrayList<>();

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
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

    public static Schedule parseSchedule(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());

        return new Schedule(
                jsonNode.get("scheduleId").asInt(),
                jsonNode.get(COURSE_ID).asInt(),
                Weekday.valueOf(jsonNode.get("weekday").asText()),
                LocalTime.parse(jsonNode.get("startTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING)),
                LocalTime.parse(jsonNode.get("endTime").asText(), DateTimeFormatter.ofPattern(TIME_STRING))
        );
    }

    public static ArrayList<Schedule> parseScheduleList(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());
        ArrayList<Schedule> schedules = new ArrayList<>();

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
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

    public static Assignment parseAssignment(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());

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

    public static ArrayList<Assignment> parseAssignmentList(HttpResponse<String> data) throws JsonProcessingException {
        if (data.body() == null || data.body().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.body());
        ArrayList<Assignment> assignments = new ArrayList<>();


        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {

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

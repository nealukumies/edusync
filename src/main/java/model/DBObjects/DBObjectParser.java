package model.DBObjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Enums.Status;
import model.Enums.Weekday;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBObjectParser {
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
                jsonNode.get("courseId").asInt(),
                jsonNode.get("studentId").asInt(),
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
                        node.get("courseId").asInt(),
                        node.get("studentId").asInt(),
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
                jsonNode.get("courseId").asInt(),
                Weekday.valueOf(jsonNode.get("weekday").asText()),
                LocalTime.parse(jsonNode.get("startTime").asText(), DateTimeFormatter.ofPattern("HH:mm")),
                LocalTime.parse(jsonNode.get("endTime").asText(), DateTimeFormatter.ofPattern("HH:mm"))
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
                        node.get("courseId").asInt(),
                        Weekday.valueOf(node.get("weekday").asText()),
                        LocalTime.parse(node.get("startTime").asText(), DateTimeFormatter.ofPattern("HH:mm")),
                        LocalTime.parse(node.get("endTime").asText(), DateTimeFormatter.ofPattern("HH:mm"))
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
                jsonNode.get("studentId").asInt(),
                jsonNode.get("courseId").asInt(),
                jsonNode.get("title").asText(),
                jsonNode.get("description").asText(),
                LocalDate.parse(jsonNode.get("deadline").asText(), DateTimeFormatter.ISO_LOCAL_DATE),
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
                String dateText = node.get("deadline").asText();
                if (dateText.contains("Sept")) {
                    dateText = dateText.replace("Sept", "Sep");
                }
                assignments.add(new Assignment(
                        node.get("assignmentId").asInt(),
                        node.get("studentId").asInt(),
                        node.get("courseId").asInt(),
                        node.get("title").asText(),
                        node.get("description").asText(),
                        LocalDate.parse(dateText, DateTimeFormatter.ISO_LOCAL_DATE),
                        Status.valueOf(node.get("status").asText())
                ));
            }
        }
        return assignments;
    }
}

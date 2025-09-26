package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.net.http.HttpResponse;

public class ScheduleHandler {
    public static HttpResponse<String> getSchedule(int scheduleId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/schedules/" + scheduleId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 201 -> {
                System.out.println("Successfully fetched schedule");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the schedule.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Schedule with the specified ID does not exist.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }

    public static HttpResponse<String> getSchedulesForCourse(int courseId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/schedules/courses/" + courseId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched schedules");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the course's schedules.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Course with the specified ID does not exist or no schedules found for this course.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }

    public static HttpResponse<String> getSchedulesForUser() {
        Connection conn = Connection.getInstance();

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/schedules/students/" + studentId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched schedules");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the student's schedules.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Student with the specified ID does not exist or no schedules found for this student.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }

    public static int createSchedule(int courseId, String weekday, String startTime, String endTime) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "course_id": "%s",
                "weekday": "%s",
                "start_time": "%s",
                "end_time": "%s"
            }""", courseId, weekday, startTime, endTime);

        HttpResponse<String> response = conn.sendPostRequest(inputString, "/schedules");

        int status = response.statusCode();

        switch (status) {
            case 201 -> {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                int scheduleId = jsonNode.get("scheduleId").asInt();

                System.out.println("Successfully created schedule.");
                return scheduleId;
            }
            case 400 -> {
                System.out.println("400 Bad Request: Missing or invalid fields in the request body.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to create the schedule.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return -1;
    }

    public static int updateSchedule(int scheduleId, int courseId, String weekday, String startTime, String endTime) {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "course_id": "%s",
                "weekday": "%s",
                "start_time": "%s",
                "end_time": "%s"
            }""", courseId, weekday, startTime, endTime);

        String endpoint = "/schedules/" + scheduleId;

        HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully updated schedule data.");
                return 1;
            }
            case 400 -> {
                System.out.println("400 Bad Request: Missing or invalid fields in the request body.");
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to update the schedule.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Schedule with the specified ID does not exist.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to update the schedule.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return -1;
    }

    public static int deleteSchedule(int scheduleId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/schedules/" + scheduleId;

        HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully deleted schedule");
                return 1;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to delete the schedule.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Schedule with the specified ID does not exist.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to delete the schedule.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return -1;
    }
}

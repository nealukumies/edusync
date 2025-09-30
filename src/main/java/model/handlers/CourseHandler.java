package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.net.http.HttpResponse;

public class CourseHandler {
    public static int createCourse(String courseName, String start_date, String end_date) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, start_date, end_date);

        HttpResponse<String> response = conn.sendPostRequest(inputString, "/courses");

        int status = response.statusCode();

        switch (status) {
            case 201 -> {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                int courseId = jsonNode.get("courseId").asInt();

                System.out.println("Successfully fetched user data.");
                return courseId;
            }
            case 400 -> {
                System.out.println("400 Bad Request: Missing or invalid fields in the request body.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to create the course.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }

        return -1;
    }

    public static int updateCourse(int courseId, String courseName, String start_date, String end_date) {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, start_date, end_date);

        String endpoint = "/courses/" + courseId;

        HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully updated course data.");
                return 1;
            }
            case 400 -> {
                System.out.println("400 Bad Request: Missing or invalid fields in the request body.");
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to update the course.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Course with the specified ID does not exist.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to update the course.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return -1;
    }

    public static int deleteCourse(int courseId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/courses/" + courseId;

        HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully deleted course.");
                return 1;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to delete the course.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Course with the specified ID does not exist.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to delete the course.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return -1;
    }

    public static HttpResponse<String> getCourses() {
        Connection conn = Connection.getInstance();

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/courses/students/" + studentId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched courses");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the student's courses.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Student with the specified ID does not exist or no courses found for this student.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }

    public static HttpResponse<String> getCourse(int courseId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/courses/" + courseId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched course data.");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the course data.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Course with the specified ID does not exist.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }
}

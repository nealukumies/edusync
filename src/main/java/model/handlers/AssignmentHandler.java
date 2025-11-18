package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.net.http.HttpResponse;

public class AssignmentHandler {
    public static final String ERROR_MESSAGE = "Error: Received status code ";

    public static HttpResponse<String> getAssignment(int assignmentId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/assignments/" + assignmentId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched assignment");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the assignment.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Assignment with the specified ID does not exist.");
            }
            default -> {
                System.out.println(ERROR_MESSAGE + status);
            }
        }
        return null;
    }

    public static HttpResponse<String> getAssignmentsForUser() {
        Connection conn = Connection.getInstance();

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/assignments/students/" + studentId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched assignments");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to the student's assignments.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Student with the specified ID does not exist or no assignments found for this student.");
            }
            default -> {
                System.out.println(ERROR_MESSAGE + status);
            }
        }
        return null;
    }

    public static int createAssignment(int courseId, String title, String description, String deadline) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
                {
                    "course_id": "%s",
                    "title": "%s",
                    "description": "%s",
                    "deadline": "%s"
                }""", courseId, title, description, deadline);

        HttpResponse<String> response = conn.sendPostRequest(inputString, "/assignments");

        int status = response.statusCode();

        switch (status) {
            case 201 -> {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                JsonNode assignmentIdNode = jsonNode.get("assignmentId");
                if (assignmentIdNode == null) {
                    return -1;
                }

                System.out.println("Successfully created assignment");
                return assignmentIdNode.asInt();
            }
            case 400 -> {
                System.out.println("400 Bad Request: Invalid input data for creating the assignment.");
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to create the assignment.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Course with the specified ID does not exist.");
            }
            default -> {
                System.out.println(ERROR_MESSAGE + status);
            }
        }
        return -1;
    }

    public static int updateAssignment(int assignmentId, int courseId, String title, String description, String deadline, String assignmentStatus) {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
                {
                    "course_id": "%s",
                    "title": "%s",
                    "description": "%s",
                    "deadline": "%s",
                    "status": "%s"
                }""", courseId, title, description, deadline, assignmentStatus);

        String endpoint = "/assignments/" + assignmentId;

        HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        System.out.println(response);
        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully updated assignment data.");
                return 1;
            }
            case 400 -> {
                System.out.println("400 Bad Request: Missing or invalid fields in the request body.");
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to update the assignment.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Assignment with the specified ID does not exist.");
            }
            case 500 -> {
                System.out.println("500 Internal Server Error: Failed to update the assignment.");
            }
            default -> {
                System.out.println(ERROR_MESSAGE + status);
            }
        }
        return -1;
    }

    public static int deleteAssignment(int assignmentId) {
        Connection conn = Connection.getInstance();

        String endpoint = "/assignments/" + assignmentId;

        HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200, 204 -> {
                System.out.println("Successfully deleted assignment");
                return 1;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to delete the assignment.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Assignment with the specified ID does not exist.");
            }
            default -> {
                System.out.println(ERROR_MESSAGE + status);
            }
        }
        return -1;

    }
}

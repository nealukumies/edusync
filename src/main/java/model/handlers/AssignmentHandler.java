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
        final Connection conn = Connection.getInstance();

        final String endpoint = "/assignments/" + assignmentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return response;
            }
        }
        return null;
    }

    public static HttpResponse<String> getAssignmentsForUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = "/assignments/students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return response;
            }
        }
        return null;
    }

    public static int createAssignment(int courseId, String title, String description, String deadline) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
                {
                    "course_id": "%s",
                    "title": "%s",
                    "description": "%s",
                    "deadline": "%s"
                }""", courseId, title, description, deadline);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/assignments");

        final int status = response.statusCode();

        switch (status) {
            case 201 -> {
                final ObjectMapper mapper = new ObjectMapper();

                final JsonNode jsonNode = mapper.readTree(response.body());

                final JsonNode assignmentIdNode = jsonNode.get("assignmentId");
                if (assignmentIdNode == null) {
                    return -1;
                }

                return assignmentIdNode.asInt();
            }
        }
        return -1;
    }

    public static int updateAssignment(int assignmentId, int courseId, String title, String description, String deadline, String assignmentStatus) {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
                {
                    "course_id": "%s",
                    "title": "%s",
                    "description": "%s",
                    "deadline": "%s",
                    "status": "%s"
                }""", courseId, title, description, deadline, assignmentStatus);

        final String endpoint = "/assignments/" + assignmentId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return 1;
            }
        }
        return -1;
    }

    public static int deleteAssignment(int assignmentId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = "/assignments/" + assignmentId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200, 204 -> {
                return 1;
            }
        }
        return -1;

    }
}

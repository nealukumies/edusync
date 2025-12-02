package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

public class AssignmentHandler {
    static final String ASSIGNMENTS_STRING = "/assignments/";

    private AssignmentHandler() {}

    public static HttpResponse<String> getAssignment(final int assignmentId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = ASSIGNMENTS_STRING + assignmentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    public static HttpResponse<String> getAssignmentsForUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = ASSIGNMENTS_STRING + "students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    public static int createAssignment(final int courseId, final String title, final String description, final String deadline) throws JsonProcessingException {
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

        if (status == 201) {
            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            final JsonNode assignmentIdNode = jsonNode.get("assignmentId");
            if (assignmentIdNode == null) {
                return -1;
            }

            return assignmentIdNode.asInt();
        }
        return -1;
    }

    public static int updateAssignment(final int assignmentId, final int courseId, final String title, final String description, final String deadline, final String assignmentStatus) {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
                {
                    "course_id": "%s",
                    "title": "%s",
                    "description": "%s",
                    "deadline": "%s",
                    "status": "%s"
                }""", courseId, title, description, deadline, assignmentStatus);

        final String endpoint = ASSIGNMENTS_STRING + assignmentId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return 1;
        }
        return -1;
    }

    public static int deleteAssignment(int assignmentId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = ASSIGNMENTS_STRING + assignmentId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200 || status == 204) {
            return 1;
        }
        return -1;
    }
}

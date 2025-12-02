package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

/** Handler class for managing assignment-related API interactions.
 */
public class AssignmentHandler {
    /** Base endpoint for assignment-related API calls.
     */
    static final String ASSIGNMENTS_STRING = "/assignments/";

    private AssignmentHandler() {}

    /** Retrieves a specific assignment by its ID.
     *
     * @param assignmentId The ID of the assignment to retrieve.
     * @return HttpResponse containing the assignment data, or null if the request failed.
     */
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

    /** Retrieves all assignments for the currently logged-in user.
     *
     * @return HttpResponse containing the assignments data, or null if the request failed.
     */
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

    /** Creates a new assignment.
     *
     * @param courseId    The ID of the course the assignment belongs to.
     * @param title       The title of the assignment.
     * @param description The description of the assignment.
     * @param deadline    The deadline of the assignment.
     * @return The ID of the newly created assignment, or -1 if creation failed.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
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

    /** Updates an existing assignment.
     *
     * @param assignmentId    The ID of the assignment to update.
     * @param courseId        The new course ID.
     * @param title           The new title.
     * @param description     The new description.
     * @param deadline        The new deadline.
     * @param assignmentStatus The new status.
     * @return 1 if update was successful, -1 otherwise.
     */
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

    /** Deletes an assignment by its ID.
     *
     * @param assignmentId The ID of the assignment to delete.
     * @return 1 if deletion was successful, -1 otherwise.
     */
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

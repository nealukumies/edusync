package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

/** Handler class for managing course-related operations. */
public class CourseHandler {
    /** String constant for courses endpoint. */
    static final String COURSES_STRING = "/courses/";

    private CourseHandler() {}
    /**
     * Creates a new course.
     *
     * @param courseName The name of the course.
     * @param startDate  The start date of the course.
     * @param endDate    The end date of the course.
     * @return The ID of the newly created course, or -1 if creation failed.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static int createCourse(final String courseName, final String startDate, final String endDate) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, startDate, endDate);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/courses");

        final int status = response.statusCode();

        if (status == 201) {
            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            return jsonNode.get("courseId").asInt();
        }
        return -1;
    }

    /**
     * Updates an existing course.
     *
     * @param courseId   The ID of the course to update.
     * @param courseName The new name of the course.
     * @param startDate  The new start date of the course.
     * @param endDate    The new end date of the course.
     * @return 1 if update was successful, -1 otherwise.
     */
    public static int updateCourse(final int courseId, final String courseName, final String startDate, final String endDate) {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, startDate, endDate);

        final String endpoint = COURSES_STRING + courseId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return 1;
        }
        return -1;
    }

    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to delete.
     * @return 1 if deletion was successful, -1 otherwise.
     */
    public static int deleteCourse(final int courseId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = COURSES_STRING + courseId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return 1;
        }
        return -1;
    }

    /**
     * Gets all courses for the logged-in student.
     *
     * @return The HttpResponse containing the list of courses, or null if not found.
     */
    public static HttpResponse<String> getCourses() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = COURSES_STRING + "students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    /**
     * Gets a specific course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return The HttpResponse containing the course data, or null if not found.
     */
    public static HttpResponse<String> getCourse(final int courseId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = COURSES_STRING + courseId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }
}

package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

public class CourseHandler {
    static final String COURSES_STRING = "/courses/";

    private CourseHandler() {}
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

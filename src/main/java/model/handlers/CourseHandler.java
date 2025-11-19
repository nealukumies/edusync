package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.net.http.HttpResponse;

public class CourseHandler {
    public static int createCourse(String courseName, String startDate, String endDate) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, startDate, endDate);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/courses");

        final int status = response.statusCode();

        switch (status) {
            case 201 -> {
                final ObjectMapper mapper = new ObjectMapper();

                final JsonNode jsonNode = mapper.readTree(response.body());

                final int courseId = jsonNode.get("courseId").asInt();

                return courseId;
            }
        }
        return -1;
    }

    public static int updateCourse(int courseId, String courseName, String startDate, String endDate) {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_name": "%s",
                "start_date": "%s",
                "end_date": "%s"
            }""", courseName, startDate, endDate);

        final String endpoint = "/courses/" + courseId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return 1;
            }
        }
        return -1;
    }

    public static int deleteCourse(int courseId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = "/courses/" + courseId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return 1;
            }
        }
        return -1;
    }

    public static HttpResponse<String> getCourses() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = "/courses/students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return response;
            }
        }
        return null;
    }

    public static HttpResponse<String> getCourse(int courseId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = "/courses/" + courseId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return response;
            }
        }
        return null;
    }
}

package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

public class ScheduleHandler {
    static final String SCHEDULES_STRING = "/schedules/";

    private ScheduleHandler() {}

    public static HttpResponse<String> getSchedule(final int scheduleId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = SCHEDULES_STRING + scheduleId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 201) {
            return response;
        }
        return null;
    }

    public static HttpResponse<String> getSchedulesForCourse(final int courseId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = SCHEDULES_STRING + "courses/" + courseId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    public static HttpResponse<String> getSchedulesForUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = SCHEDULES_STRING + "students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    public static int createSchedule(final int courseId, final String weekday, final String startTime, final String endTime) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_id": "%s",
                "weekday": "%s",
                "start_time": "%s",
                "end_time": "%s"
            }""", courseId, weekday, startTime, endTime);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/schedules");

        final int status = response.statusCode();

        if (status == 201) {
            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            return jsonNode.get("scheduleId").asInt();
        }
        return -1;
    }

    public static int updateSchedule(final int scheduleId, final int courseId, final String weekday, final String startTime, final String endTime) {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "course_id": "%s",
                "weekday": "%s",
                "start_time": "%s",
                "end_time": "%s"
            }""", courseId, weekday, startTime, endTime);

        final String endpoint = SCHEDULES_STRING + scheduleId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return 1;
        }
        return -1;
    }

    public static int deleteSchedule(final int scheduleId) {
        final Connection conn = Connection.getInstance();

        final String endpoint = SCHEDULES_STRING + scheduleId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return 1;
        }
        return -1;
    }
}

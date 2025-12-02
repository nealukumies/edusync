package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.net.http.HttpResponse;

/** Handler class for managing schedules via HTTP requests.
 */
public class ScheduleHandler {
    /** Endpoint string for schedules.
     */
    static final String SCHEDULES_STRING = "/schedules/";

    private ScheduleHandler() {}

    /**
     * Fetches a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule.
     * @return HttpResponse containing the schedule, or null if the request failed.
     */
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

    /**
     * Fetches all schedules for a specific course.
     *
     * @param courseId The ID of the course.
     * @return HttpResponse containing the schedules, or null if the request failed.
     */
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

    /**
     * Fetches all schedules for the currently logged-in user.
     *
     * @return HttpResponse containing the schedules, or null if the request failed.
     */
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

    /**
     * Creates a new schedule.
     *
     * @param courseId  The ID of the course.
     * @param weekday   The weekday for the schedule.
     * @param startTime The start time for the schedule.
     * @param endTime   The end time for the schedule.
     * @return The ID of the newly created schedule, or -1 if creation failed.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
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

    /**
     * Updates an existing schedule.
     *
     * @param scheduleId The ID of the schedule to be updated.
     * @param courseId   The new course ID.
     * @param weekday    The new weekday.
     * @param startTime  The new start time.
     * @param endTime    The new end time.
     * @return 1 if update was successful, -1 otherwise.
     */
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

    /**
     * Deletes a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule to be deleted.
     * @return 1 if deletion was successful, -1 otherwise.
     */
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

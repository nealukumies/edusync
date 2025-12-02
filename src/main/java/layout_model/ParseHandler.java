package layout_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.db_objects.DBObjectParser;
import model.db_objects.Schedule;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;
import model.handlers.ScheduleHandler;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/** Utility class for parsing and fetching data related to courses, assignments, and schedules.
 */
public class ParseHandler {

    private ParseHandler() {}

    /**
     * Fetches all courses for the user.
     *
     * @return A list of courses.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Course> getCourses() throws JsonProcessingException {
        final HttpResponse<String> res = CourseHandler.getCourses();
        if (res != null) {
            return DBObjectParser.parseCourseList(res);
        }
        return new ArrayList<>();
    }


    /**
     * Fetches all assignments for the user.
     *
     * @return A list of assignments.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Assignment> getAssignments() throws JsonProcessingException {
        final HttpResponse<String> res = AssignmentHandler.getAssignmentsForUser();
        if (res != null) {
            return DBObjectParser.parseAssignmentList(res);
        }
        return new ArrayList<>();
    }

    /**
     * Fetches the assignments for a given course.
     *
     * @param course The course for which to fetch assignments.
     * @return A list of assignments associated with the course.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Assignment> getAssignmentsForCourse(final Course course) throws JsonProcessingException {
        final List<Assignment> assignments = new ArrayList<>();
        final List<Assignment> data = getAssignments();
        for (final Assignment assignment : data) {
            if (assignment.getCourseId() == course.getCourseId()) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    /**
     * Fetches the schedules for a given course.
     *
     * @param course The course for which to fetch schedules.
     * @return A list of schedules associated with the course.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static List<Schedule> getSchedulesForCourse(final Course course) throws JsonProcessingException {
        final HttpResponse<String> res = ScheduleHandler.getSchedulesForCourse(course.getCourseId());
        if (res != null) {
            return DBObjectParser.parseScheduleList(res);
        }
        return new ArrayList<>();
    }
}

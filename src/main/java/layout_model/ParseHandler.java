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

public class ParseHandler {

    private ParseHandler() {}

    public static List<Course> getCourses() throws JsonProcessingException {
        final HttpResponse<String> res = CourseHandler.getCourses();
        if (res != null) {
            return DBObjectParser.parseCourseList(res);
        }
        return new ArrayList<>();
    }

    public static List<Assignment> getAssignments() throws JsonProcessingException {
        final HttpResponse<String> res = AssignmentHandler.getAssignmentsForUser();
        if (res != null) {
            return DBObjectParser.parseAssignmentList(res);
        }
        return new ArrayList<>();
    }

    public static List<Assignment> getAssignmentsForCourse(Course course) throws JsonProcessingException {
        final List<Assignment> assignments = new ArrayList<>();
        final List<Assignment> data = getAssignments();
        for (final Assignment assignment : data) {
            if (assignment.getCourseId() == course.getCourseId()) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    public static List<Schedule> getSchedulesForCourse(Course course) throws JsonProcessingException {
        final HttpResponse<String> res = ScheduleHandler.getSchedulesForCourse(course.getCourseId());
        if (res != null) {
            return DBObjectParser.parseScheduleList(res);
        }
        return new ArrayList<>();
    }
}

package layout_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.DBObjectParser;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ParseHandler {

    public static List<Course> getCourses() throws JsonProcessingException {
        HttpResponse<String> res = CourseHandler.getCourses();
        if (res != null) {
            return DBObjectParser.parseCourseList(res);
        }
        return null;
    }

    public static List<Assignment> getAssignments() throws JsonProcessingException {
        HttpResponse<String> res = AssignmentHandler.getAssignmentsForUser();
        if (res != null) {
            return DBObjectParser.parseAssignmentList(res);
        }
        return null;
    }

    public static List<Assignment> getAssignmentsForCourse(Course course) throws JsonProcessingException {
        List<Assignment> assignments = new ArrayList<>();
        List<Assignment> data = getAssignments();
        for (Assignment assignment : data) {
            if (assignment.getCourseId() == course.getCourseId()) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }
}

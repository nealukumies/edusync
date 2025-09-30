package layout_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.DBObjects.Course;
import model.DBObjects.DBObjectParser;
import model.handlers.CourseHandler;

import java.net.http.HttpResponse;
import java.util.List;

public class ParseHandler {

    public static List<Course> getCourses() throws JsonProcessingException {
        HttpResponse<String> res = CourseHandler.getCourses();
        if (res != null) {
            return DBObjectParser.parseCourseList(res);
        }
        return null;
    }
}

package model;

import database.AssignmentDao;
import database.CourseDao;

import java.util.List;

public class DataFetcher {

    public static List<Assignment> fetchUserAssignments(int userId) {
        AssignmentDao assignmentDao = new AssignmentDao();

        return assignmentDao.getAssignments(userId);
    }

    public static Course fetchCourse(int courseId) {
        CourseDao courseDao = new CourseDao();

        return courseDao.getCourseById(courseId);
    }

}

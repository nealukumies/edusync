package component;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.Enums.Status;

import java.sql.Timestamp;
import java.util.List;

public class AssignmentList {

    public static TableView<ViewableAssignment> createList(List<Assignment> assignmentList, List<Course> courses) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssignmentList.class.getResource("/components/AssignmentTable.fxml"));
            TableView<ViewableAssignment> table = fxmlLoader.load();
            for (Assignment assignment : assignmentList) {
                String title = assignment.getTitle();
                int courseId = assignment.getCourseId() != null ? assignment.getCourseId() : -1;
                Timestamp deadline = assignment.getDeadline();
                Status status = assignment.getStatus();
                String courseName = getCourseName(courses, courseId);

                ViewableAssignment viewableAssignment = new ViewableAssignment(title, courseId, deadline, status, courseName);
                table.getItems().add(viewableAssignment);
            }
            table.sort();
            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCourseName(List<Course> courses, int courseId) {
        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                return c.getCourseName();
            }
        }
        return null;
    }
}

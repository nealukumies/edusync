package component;

import controller.MainController;
import controller.SubController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import model.db_objects.Assignment;
import model.db_objects.Course;
import view.MainView;

import java.util.List;

/**
 * TableView component to display a list of assignments.
 */
public class AssignmentList {
    MainController mainController;

    public AssignmentList(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Creates new TableView element with list of assignments.
     *
     * @param assignmentList List of assignments
     * @param courses        List of courses
     * @return
     */
    public TableView<ViewableAssignment> createList(List<Assignment> assignmentList, List<Course> courses) {
        if (assignmentList.isEmpty()) {
            return null;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssignmentList.class.getResource("/components/AssignmentTable.fxml"));
            fxmlLoader.setResources(MainView.getBundle());
            TableView<ViewableAssignment> table = fxmlLoader.load();
            SubController subController = fxmlLoader.getController();
            subController.setMainViewController(mainController);
            subController.initializeFully();
            for (Assignment assignment : assignmentList) {
                int courseId = assignment.getCourseId() != null ? assignment.getCourseId() : -1;
                Course course = getCourse(courses, courseId);

                ViewableAssignment viewableAssignment = new ViewableAssignment(assignment, course);
                table.getItems().add(viewableAssignment);
            }
            table.sort();
            return table;
        } catch (Exception e) {}
        return null;
    }

    /**
     * Returns a course from the course list
     *
     * @param courses  List of courses
     * @param courseId ID of the course being searched
     * @return
     */
    public Course getCourse(List<Course> courses, int courseId) {
        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                return c;
            }
        }
        return null;
    }
}

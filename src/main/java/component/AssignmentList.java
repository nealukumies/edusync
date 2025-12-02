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
    /** MainController instance */
    MainController mainController;

    /**
     * Constructor for AssignmentList
     *
     * @param mainController MainController instance
     */
    public AssignmentList(final MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Creates new TableView element with list of assignments.
     *
     * @param assignmentList List of assignments
     * @param courses        List of courses
     * @return
     */
    public TableView<ViewableAssignment> createList(final List<Assignment> assignmentList, final List<Course> courses) {
        if (assignmentList.isEmpty()) {
            return null;
        }
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(AssignmentList.class.getResource("/components/AssignmentTable.fxml"));
            fxmlLoader.setResources(MainView.getBundle());
            final TableView<ViewableAssignment> table = fxmlLoader.load();
            final SubController subController = fxmlLoader.getController();
            subController.setMainViewController(mainController);
            subController.initializeFully();
            for (final Assignment assignment : assignmentList) {
                final int courseId = assignment.getCourseId() != null ? assignment.getCourseId() : -1;
                final Course course = getCourse(courses, courseId);

                final ViewableAssignment viewableAssignment = new ViewableAssignment(assignment, course);
                table.getItems().add(viewableAssignment);
            }
            table.sort();
            return table;
        } catch (Exception e) {
            //empty catch block to ignore error
        }
        return null;
    }

    /**
     * Returns a course from the course list
     *
     * @param courses  List of courses
     * @param courseId ID of the course being searched
     * @return
     */
    public Course getCourse(final List<Course> courses, final int courseId) {
        for (final Course c : courses) {
            if (c.getCourseId() == courseId) {
                return c;
            }
        }
        return null;
    }
}

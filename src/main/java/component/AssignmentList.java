package component;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import model.DBObjects.Assignment;

import java.util.List;

public class AssignmentList {

    public static TableView<ViewableAssignment> createList(List<Assignment> assignmentList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssignmentList.class.getResource("/components/AssignmentTable.fxml"));
            TableView<ViewableAssignment> table = fxmlLoader.load();
            for (Assignment assignment : assignmentList) {
                ViewableAssignment viewableAssignment = new ViewableAssignment(assignment.getTitle(), assignment.getCourseId(), assignment.getDeadline(), assignment.getStatus());
                table.getItems().add(viewableAssignment);
            }
            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

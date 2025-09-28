package controller;

import component.AssignmentList;
import component.ViewableAssignment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.DBObjects.Assignment;
import model.DBObjects.DBObjectParser;
import model.handlers.AssignmentHandler;

import java.util.ArrayList;
import java.util.List;

public class DashboardController extends SubController {
    @FXML
    private VBox assignments;
    @FXML
    private List<Assignment> assignmentList;

    public void initialize() {
        this.assignmentList = new ArrayList<>();
        loadAssignments();
    }

    public void loadAssignments() {
        try {
            List<Assignment> data = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
            if (data != null) {
                assignmentList.addAll(data);
                generateAssignments();
            } else {
                throw new Exception("Assignments not found");
            }
        } catch (Exception e) {
            assignments.getChildren().clear();
            Label label = new Label("You have no assignments");
            assignments.getChildren().add(label);
        }
    }

    public void generateAssignments() {
        assignments.getChildren().clear();
        if (!assignmentList.isEmpty()) {
            TableView<ViewableAssignment> table = AssignmentList.createList(assignmentList);
            assignments.getChildren().add(table);
        }
    }

    @Override
    public void initializeFully() {

    }
}

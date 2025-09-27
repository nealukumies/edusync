package controller;

import component.ViewableAssignment;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Enums.Status;

import java.util.Date;

public class AssignmentTableController extends SubController {
    @FXML
    TableColumn<ViewableAssignment, String> titleCol;
    @FXML
    TableColumn<ViewableAssignment, Integer> courseCol;
    @FXML
    TableColumn<ViewableAssignment, Date> deadlineCol;
    @FXML
    TableColumn<ViewableAssignment, Status> statusCol;

    public void initialize() {
        titleCol.setCellValueFactory(new PropertyValueFactory<ViewableAssignment, String>("title"));
        courseCol.setCellValueFactory(new PropertyValueFactory<ViewableAssignment, Integer>("course"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<ViewableAssignment, Date>("deadline"));
        statusCol.setCellValueFactory(new PropertyValueFactory<ViewableAssignment, Status>("status"));
    }

    @Override
    public void initializeFully() {

    }
}

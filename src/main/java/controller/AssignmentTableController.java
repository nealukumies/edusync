package controller;

import component.ViewableAssignment;
import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Enums.Status;

import java.util.Date;

public class AssignmentTableController extends SubController {
    @FXML
    TableView<ViewableAssignment> table;
    @FXML
    TableColumn<ViewableAssignment, String> titleCol;
    @FXML
    TableColumn<ViewableAssignment, Integer> courseCol;
    @FXML
    TableColumn<ViewableAssignment, Date> deadlineCol;
    @FXML
    TableColumn<ViewableAssignment, Status> statusCol;

    public void initialize() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        deadlineCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(deadlineCol);
        table.sort();
    }

    @Override
    public void initializeFully() {

        titleCol.setCellFactory(tc -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink();

            {
                link.setOnAction(event -> {
                    ViewableAssignment viewableAssignment = getTableRow().getItem();
                    if (viewableAssignment != null) {
                        System.out.println(getMainController());
                        getMainController().setAssignment(viewableAssignment.getAssignment());
                        getMainController().setCourse(viewableAssignment.getCourseObject());
                        getMainController().changePage(Page.ASSIGNMENT_PAGE);
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                ViewableAssignment viewableAssignment = getTableRow().getItem();
                if (empty || viewableAssignment == null) {
                    setGraphic(null);
                    return;
                }
                link.setText(viewableAssignment.getTitle());
                setGraphic(link);
            }
        });
    }
}

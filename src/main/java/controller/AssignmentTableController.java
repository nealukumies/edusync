package controller;

import component.ViewableAssignment;
import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.enums.Status;

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

    public void initializeTitleColumn() {
        titleCol.setCellFactory(tc -> new TitleCell());
    }

    public void initializeCourseColumn() {
        courseCol.setCellFactory(tc -> new CourseCell());
    }

    @Override
    public void initializeFully() {
        initializeTitleColumn();
        initializeCourseColumn();
    }

    private class TitleCell extends TableCell<ViewableAssignment, String> {
        private final Hyperlink link = new Hyperlink();

        public TitleCell() {
            link.setOnAction(event -> {
                final ViewableAssignment viewableAssignment = getTableRow().getItem();
                if (viewableAssignment != null) {
                    getMainController().setAssignment(viewableAssignment.getAssignment());
                    getMainController().setCourse(viewableAssignment.getCourseObject());
                    getMainController().changePage(Page.ASSIGNMENT_PAGE);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            final ViewableAssignment viewableAssignment = getTableRow().getItem();
            if (empty || viewableAssignment == null) {
                setGraphic(null);
                return;
            }
            link.setText(viewableAssignment.getTitle());
            setGraphic(link);
        }
    }

    private class CourseCell extends TableCell<ViewableAssignment, Integer> {
        private final Hyperlink link = new Hyperlink();

        public CourseCell() {
            link.setOnAction(event -> {
                final ViewableAssignment viewableAssignment = getTableRow().getItem();
                if (viewableAssignment != null) {
                    getMainController().setCourse(viewableAssignment.getCourseObject());
                    getMainController().changePage(Page.COURSE_PAGE);
                }
            });
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            final ViewableAssignment viewableAssignment = getTableRow().getItem();
            if (empty || viewableAssignment == null) {
                setGraphic(null);
                return;
            }
            link.setText(viewableAssignment.getCourseObject().getCourseName());
            setGraphic(link);
        }
    }
}

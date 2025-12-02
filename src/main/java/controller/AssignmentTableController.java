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

/**
 * Controller for the assignment table view.
 */
public class AssignmentTableController extends SubController {
    /**  TableView for assignments */
    @FXML
    TableView<ViewableAssignment> table;
    /**  Column for assignment title */
    @FXML
    TableColumn<ViewableAssignment, String> titleCol;
    /**  Column for assignment course */
    @FXML
    TableColumn<ViewableAssignment, Integer> courseCol;
    /**  Column for assignment deadline */
    @FXML
    TableColumn<ViewableAssignment, Date> deadlineCol;
    /**  Column for assignment status */
    @FXML
    TableColumn<ViewableAssignment, Status> statusCol;

    /**
     * Initializes the assignment table with appropriate cell value factories and sorting.
     */
    public void initialize() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        deadlineCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(deadlineCol);
        table.sort();
    }

    /**
     * Initializes the title column with hyperlinks to assignment pages.
     */
    public void initializeTitleColumn() {
        titleCol.setCellFactory(tc -> new TitleCell());
    }

    /**
     * Initializes the course column with hyperlinks to course pages.
     */
    public void initializeCourseColumn() {
        courseCol.setCellFactory(tc -> new CourseCell());
    }

    @Override
    public void initializeFully() {
        initializeTitleColumn();
        initializeCourseColumn();
    }

    /** Cell class for displaying assignment titles as hyperlinks in the title column. */
    private class TitleCell extends TableCell<ViewableAssignment, String> {
        /** Hyperlink to navigate to the assignment page */
        private final Hyperlink link = new Hyperlink();

        /** Constructor sets up the hyperlink action to navigate to the assignment page. */
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
        protected void updateItem(final String item, final boolean empty) {
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

    /** Cell class for displaying course names as hyperlinks in the course column. */
    private class CourseCell extends TableCell<ViewableAssignment, Integer> {
        /** Hyperlink to navigate to the course page */
        private final Hyperlink link = new Hyperlink();

        /** Constructor sets up the hyperlink action to navigate to the course page. */
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
        protected void updateItem(final Integer item, final boolean empty) {
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

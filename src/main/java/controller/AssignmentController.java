package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.enums.Status;
import model.handlers.AssignmentHandler;
import view.MainView;

import java.util.Optional;

/**
 * Controller for viewing and managing a specific assignment.
 */
public class AssignmentController extends SubController {
    /** Label for assignment title */
    @FXML
    private Label title;
    /** Hyperlink for course name */
    @FXML
    private Hyperlink courseName;
    /** Label for assignment description */
    @FXML
    private Label desc;
    /** Label for assignment deadline */
    @FXML
    private Label deadline;
    /** ChoiceBox for assignment status */
    @FXML
    private ChoiceBox<Status> status;
    /** ChoiceBox for assignment status */
    @FXML
    private Button saveButton;
    /** Button to edit the assignment */
    @FXML
    private Button editButton;
    /** Button to delete the assignment */
    @FXML
    private Button deleteButton;

    /** The assignment being viewed or edited */
    private Assignment assignment;

    @Override
    public void initializeFully() {
        this.assignment = getMainController().getAssignment();
        final Course course = getMainController().getCourse();

        title.setText(assignment.getTitle());
        courseName.setText(course.getCourseName());
        desc.setText(assignment.getDescription());
        deadline.setText(assignment.getDeadline().toString());

        status.getItems().addAll(Status.values());
        initializeStatus();

        saveButton.setOnAction(e -> {
            final int idInt = assignment.getAssignmentId();
            final int courseIdInt = course.getCourseId();
            final String titleString = assignment.getTitle();
            final String descString = assignment.getDescription();

            final String deadlineString = assignment.getDeadline().toString();
            final String statusString = status.getSelectionModel().getSelectedItem().getDbValue();

            AssignmentHandler.updateAssignment(idInt, courseIdInt, titleString, descString, deadlineString, statusString);
            getMainController().goToPrevPage();
        });

        deleteButton.setOnAction(e -> {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            final String titleOnAction = MainView.getBundle().getString("delete_alert_title");
            final String header = MainView.getBundle().getString("delete_alert_header");
            final String content = MainView.getBundle().getString("delete_alert_content");
            alert.setTitle(titleOnAction);
            alert.setHeaderText(header);
            alert.setContentText(content);
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                AssignmentHandler.deleteAssignment(assignment.getAssignmentId());
                getMainController().setAssignment(null);
                getMainController().setCourse(null);
                getMainController().goToPrevPage();
            }
        });

        editButton.setOnAction(e -> {
            getMainController().setAssignment(assignment);
            getMainController().setCourse(course);
            getMainController().changePage(Page.EDIT_ASSIGNMENT_PAGE);
        });

        courseName.setOnAction(e -> {
            this.getMainController().setCourse(course);
            this.getMainController().changePage(Page.COURSE_PAGE);
        });
    }

    /** Initializes the status ChoiceBox to reflect the current status of the assignment. */
    public void initializeStatus() {
        for (int i = 0; i < status.getItems().size(); i++) {
            if (status.getItems().get(i).equals(assignment.getStatus())) {
                status.getSelectionModel().select(i);
                break;
            }
        }
    }
}

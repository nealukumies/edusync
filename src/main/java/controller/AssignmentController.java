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

public class AssignmentController extends SubController {
    @FXML
    private Label title;
    @FXML
    private Hyperlink courseName;
    @FXML
    private Label desc;
    @FXML
    private Label deadline;
    @FXML
    private ChoiceBox<Status> status;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

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
            int idInt = assignment.getAssignmentId();
            int courseIdInt = course.getCourseId();
            String titleString = assignment.getTitle();
            String descString = assignment.getDescription();

            String deadlineString = assignment.getDeadline().toString();
            String statusString = status.getSelectionModel().getSelectedItem().getDbValue();

            AssignmentHandler.updateAssignment(idInt, courseIdInt, titleString, descString, deadlineString, statusString);
            getMainController().goToPrevPage();
        });

        deleteButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String titleOnAction = MainView.getBundle().getString("delete_alert_title");
            String header = MainView.getBundle().getString("delete_alert_header");
            String content = MainView.getBundle().getString("delete_alert_content");
            alert.setTitle(titleOnAction);
            alert.setHeaderText(header);
            alert.setContentText(content);
            Optional<ButtonType> result = alert.showAndWait();
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

    public void initializeStatus() {
        for (int i = 0; i < status.getItems().size(); i++) {
            if (status.getItems().get(i).equals(assignment.getStatus())) {
                status.getSelectionModel().select(i);
                break;
            }
        }
    }
}

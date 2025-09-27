package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainController {
    @FXML
    private HBox headerContent;
    @FXML
    private StackPane content;
    @FXML
    private Hyperlink mainTitle;

    private Page currentPage;

    public void initialize() throws IOException {
        changePage(Page.FRONT_PAGE);
        mainTitle.setOnAction(e -> changePage(Page.COURSE_LIST_PAGE));
    }

    public void generateHeader() {
        // Once we have a user model, this method will determine whether to show personal header or generic
        // But for now, just use the generic "log in" header
        headerContent.getChildren().clear();

        // Dynamically forces all content to the right edge of the container
        Region spacer = new Region();
        headerContent.setHgrow(spacer, Priority.ALWAYS);

        Hyperlink loginText = new Hyperlink("Log In");
        loginText.getStyleClass().add("medium-title");
        loginText.getStyleClass().add("link");
        loginText.setOnAction(e -> {
            changePage(Page.LOGIN_PAGE);
        });
        headerContent.getChildren().addAll(spacer, loginText);
    }

    public void changePage(Page page) {
        if (page == currentPage) {
            return;
        }
        generateHeader();

        content.getChildren().clear();

        switch (page) {
            case FRONT_PAGE:
                loadPage("/view/FrontpageView.fxml");
                break;
            case DASHBOARD_PAGE:
                loadPage("/DashboardView.fxml");
                break;
            case ADD_COURSE_PAGE:
                loadPage("/view/AddCourseView.fxml");
                break;
            case ADD_ASSIGNMENT_PAGE:
                loadPage("/view/AddAssignmentView.fxml");
                break;
            case LOGIN_PAGE:
                loadPage("/view/LogInView.fxml");
                break;
            case REGISTER_PAGE:
                loadPage("/view/RegisterView.fxml");
                break;
            case COURSE_LIST_PAGE:
                loadPage("/view/CourseListView.fxml");
                break;
        }

        this.currentPage = page;
    }

    public void loadPage(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();
            SubController subController = fxmlLoader.getController();
            subController.setMainViewController(this);
            subController.initializeFully();
            content.getChildren().add(root);
            //content.getChildren().add(new AssignmentList(testList).createList());
        }
        catch (IOException e) {
            e.printStackTrace();
            Label  error = new Label();
            error.getStyleClass().add("error");
            error.setText("Error: " + e.getMessage());
            error.setWrapText(true);
            content.getChildren().add(error);
        }
    }
}

package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import model.Singletons.Account;

import java.io.IOException;

public class MainController {
    @FXML
    private HBox headerContent;
    @FXML
    private StackPane content;
    @FXML
    private Hyperlink mainTitle;

    private Page currentPage;
    private Account account = null;

    public void initialize() throws IOException {
        this.account = Account.getInstance();
        changePage(Page.FRONT_PAGE);
        mainTitle.setOnAction(e -> changePage(Page.COURSE_LIST_PAGE));
    }

    public void generateHeader() {
        headerContent.getChildren().clear();

        // Dynamically forces all content to the right edge of the container
        Region spacer = new Region();
        headerContent.setHgrow(spacer, Priority.ALWAYS);

        if (account.isLoggedIn()) {
            // For now, just add username
            Label label = new Label(account.getName());
            label.getStyleClass().add("medium-title");
            headerContent.getChildren().addAll(spacer, label);
        } else {
            Hyperlink loginText = new Hyperlink("Log In");
            loginText.getStyleClass().add("medium-title");
            loginText.getStyleClass().add("link");
            loginText.setOnAction(e -> {
                changePage(Page.LOGIN_PAGE);
            });
            headerContent.getChildren().addAll(spacer, loginText);
        }

    }

    public void changePage(Page page) {
        if (page == currentPage) {
            return;
        }
        generateHeader();

        content.getChildren().clear();

        if (page == Page.FRONT_PAGE && account.isLoggedIn()) {
            page = Page.DASHBOARD_PAGE;
        }

        switch (page) {
            case FRONT_PAGE:
                loadPage("/view/FrontpageView.fxml");
                break;
            case DASHBOARD_PAGE:
                loadPage("/view/DashboardView.fxml");
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
        } catch (IOException e) {
            e.printStackTrace();
            Label error = new Label();
            error.getStyleClass().add("error");
            error.setText("Error: " + e.getMessage());
            error.setWrapText(true);
            content.getChildren().add(error);
        }
    }
}

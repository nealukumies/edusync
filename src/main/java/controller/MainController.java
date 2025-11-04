package controller;

import enums.Language;
import enums.Page;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.Singletons.Account;
import model.handlers.UserHandler;
import view.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private HBox headerContent;
    @FXML
    private StackPane content;
    @FXML
    private Hyperlink mainTitle;
    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    @FXML
    private BorderPane root;
    @FXML
    private ComboBox<Language> languageSelect;

    private Page currentPage;
    private List<PageMemento> pageHistory;
    private List<PageMemento> pageBackHistory;
    private Account account = null;
    private Assignment assignment = null;
    private Course course = null;

    public void initialize() throws IOException {
        this.pageHistory = new ArrayList<>();
        this.pageBackHistory = new ArrayList<>();
        this.account = Account.getInstance();
        this.changePage(Page.FRONT_PAGE);
        this.mainTitle.setOnAction(e -> changePage(Page.FRONT_PAGE));

        // Custom cell factory to display images of flags
        this.languageSelect.setCellFactory(cb -> new ListCell<>() {
            private final ImageView flagView = new ImageView();

            @Override
            protected void updateItem(Language item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    flagView.setImage(new Image(getClass().getResourceAsStream("/flags/" + item.getCountry().toLowerCase() + ".png")));
                    flagView.setFitHeight(16);
                    flagView.setPreserveRatio(true);
                    setText(item.getDisplayName());
                    setGraphic(flagView);
                }
            }
        });
        this.languageSelect.getItems().addAll(Language.values());
        this.languageSelect.setButtonCell(this.languageSelect.getCellFactory().call(null));

        // Set currently displayed language to default
        this.languageSelect.setValue(MainView.getCurrentLanguage());
        this.languageSelect.setOnAction(e -> {
            MainView.setLanguage(this.languageSelect.getValue());
            this.refreshCurrentPage();
        });

        this.backButton.setOnAction(e -> {
            goToPrevPage();
        });
        this.forwardButton.setOnAction(e -> {
            goToNextPage();
        });

        this.pageHistory.clear();
        updateButtons();
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

            Hyperlink logout = new Hyperlink("> Log out");
            logout.setOnAction(actionEvent -> {
                UserHandler.logoutUser();
                changePage(Page.FRONT_PAGE);
                pageHistory.clear();
                updateButtons();
            });

            headerContent.getChildren().addAll(spacer, label, logout);
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

    public PageMemento createPageMemento() {
        return new PageMemento(this.currentPage, this.course, this.assignment);
    }

    public void updateButtons() {
        backButton.setDisable(this.pageHistory.isEmpty());
        forwardButton.setDisable(this.pageBackHistory.isEmpty());
    }

    public void goToPrevPage() {
        if (!this.pageHistory.isEmpty()) {
            PageMemento current = this.createPageMemento();
            PageMemento prev = this.pageHistory.remove(this.pageHistory.size() - 1);
            this.pageBackHistory.add(current);
            this.restoreState(prev);
        }
    }

    public void goToNextPage() {
        if (!this.pageBackHistory.isEmpty()) {
            PageMemento current = this.createPageMemento();
            PageMemento prev = this.pageBackHistory.remove(this.pageBackHistory.size() - 1);
            this.pageHistory.add(current);
            this.restoreState(prev);
        }
    }

    public void clearPageBackHistory() {
        this.pageBackHistory.clear();
    }

    public void restoreState(PageMemento state) {
        this.course = state.getCourse();
        this.assignment = state.getAssignment();
        this.changePageWithoutChangingHistory(state.getPage());
    }

    public void refreshCurrentPage() {
        generateHeader();
        content.getChildren().clear();
        this.loadPageURL(currentPage);
        updateButtons();
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

        this.loadPageURL(page);

        this.pageHistory.add(this.createPageMemento());
        this.pageBackHistory.clear();
        this.currentPage = page;
        updateButtons();
    }

    public void changePageWithoutChangingHistory(Page page) {
        if (page == currentPage) {
            return;
        }
        generateHeader();

        content.getChildren().clear();

        if (page == Page.FRONT_PAGE && account.isLoggedIn()) {
            page = Page.DASHBOARD_PAGE;
        }

        this.loadPageURL(page);
        this.currentPage = page;
        updateButtons();
    }

    public void loadPageURL(Page page) {
        switch (page) {
            case FRONT_PAGE -> loadPage("/view/FrontpageView.fxml");
            case DASHBOARD_PAGE -> loadPage("/view/DashboardView.fxml");
            case ADD_COURSE_PAGE -> loadPage("/view/AddCourseView.fxml");
            case ADD_ASSIGNMENT_PAGE -> loadPage("/view/AddAssignmentView.fxml");
            case LOGIN_PAGE -> loadPage("/view/LogInView.fxml");
            case REGISTER_PAGE -> loadPage("/view/RegisterView.fxml");
            case COURSE_LIST_PAGE -> loadPage("/view/CourseListView.fxml");
            case ASSIGNMENT_PAGE -> loadPage("/view/AssignmentView.fxml");
            case COURSE_PAGE -> loadPage("/view/CourseView.fxml");
            case CALENDAR_PAGE -> loadPage("/view/CalendarView.fxml");
            case ADD_SCHEDULE_PAGE -> loadPage("/view/AddScheduleView.fxml");
            case EDIT_COURSE_PAGE -> loadPage("/view/EditCourseView.fxml");
            case EDIT_ASSIGNMENT_PAGE -> loadPage("/view/EditAssignmentView.fxml");
        }
    }

    public void loadPage(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            fxmlLoader.setResources(MainView.getBundle());
            Parent root = fxmlLoader.load();
            SubController subController = fxmlLoader.getController();
            subController.setMainViewController(this);
            subController.initializeFully();
            content.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            Label error = new Label();
            error.getStyleClass().add("error");
            error.setText("Error: " + e.getMessage());
            error.setWrapText(true);
            content.getChildren().add(error);
        }
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<PageMemento> getPageHistory() {
        return pageHistory;
    }

    public BorderPane getRoot() {
        return root;
    }
}

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
import model.db_objects.Assignment;
import model.db_objects.Course;
import model.singletons.Account;
import model.handlers.UserHandler;
import view.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the main view, managing navigation and page loading.
 */
public class MainController {
    /** Header content area */
    @FXML
    private HBox headerContent;
    /** Content area where different pages are loaded */
    @FXML
    private StackPane content;
    /** Main title hyperlink for navigation to the front page */
    @FXML
    private Hyperlink mainTitle;
    /** Backward navigation button */
    @FXML
    private Button backButton;
    /** Forward navigation button */
    @FXML
    private Button forwardButton;
    /** The root BorderPane of the main view */
    @FXML
    private BorderPane root;
    /** Language selection ComboBox */
    @FXML
    private ComboBox<Language> languageSelect;

    /** The currently displayed page. */
    private Page currentPage;
    /** The history stack for backward navigation. */
    private List<PageMemento> pageHistory;
    /** The history stack for forward navigation. */
    private List<PageMemento> pageBackHistory;
    /** The current user's account. */
    private Account account = null;
    /** The current assignment being viewed or edited. */
    private Assignment assignment = null;
    /** The current course being viewed or edited. */
    private Course course = null;

    /** Initializes the MainController, setting up navigation and language selection.
     */
    public void initialize() {
        this.pageHistory = new ArrayList<>();
        this.pageBackHistory = new ArrayList<>();
        this.account = Account.getInstance();
        this.mainTitle.setOnAction(e -> changePage(Page.FRONT_PAGE));

        // Custom cell factory to display images of flags
        this.languageSelect.setCellFactory(cb -> new ListCell<>() {
            /** ImageView to display the flag */
            private final ImageView flagView = new ImageView();

            @Override
            protected void updateItem(final Language item, final boolean empty) {
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

        this.backButton.setOnAction(e -> goToPrevPage());
        this.forwardButton.setOnAction(e -> goToNextPage());

        this.changePage(Page.FRONT_PAGE);
        this.pageHistory.clear();
        updateButtons();
    }

    /** Generates the header content based on the user's login status.
     */
    public void generateHeader() {
        headerContent.getChildren().clear();

        // Dynamically forces all content to the right edge of the container
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        if (account.isLoggedIn()) {
            // For now, just add username
            final Label label = new Label(account.getName());
            label.getStyleClass().add("medium-title");

            final String logOutText = MainView.getBundle().getString("Header_Log_out");
            final Hyperlink logout = new Hyperlink(logOutText);
            logout.setOnAction(actionEvent -> {
                UserHandler.logoutUser();
                changePage(Page.FRONT_PAGE);
                pageHistory.clear();
                updateButtons();
            });

            headerContent.getChildren().addAll(spacer, label, logout);
        } else {
            final String logOutText = MainView.getBundle().getString("Header_Log_In");
            final Hyperlink loginText = new Hyperlink(logOutText);
            loginText.getStyleClass().add("medium-title");
            loginText.getStyleClass().add("link");
            loginText.setOnAction(e -> changePage(Page.LOGIN_PAGE));
            headerContent.getChildren().addAll(spacer, loginText);
        }

    }

    /** Creates a PageMemento representing the current state of the MainController.
     *
     * @return A PageMemento containing the current page, course, and assignment.
     */
    public PageMemento createPageMemento() {
        return new PageMemento(this.currentPage, this.course, this.assignment);
    }

    /** Updates the state of the back and forward navigation buttons.
     */
    public void updateButtons() {
        backButton.setDisable(this.pageHistory.isEmpty());
        forwardButton.setDisable(this.pageBackHistory.isEmpty());
    }

    /** Goes to the previous page in the navigation history, if available.
     */
    public void goToPrevPage() {
        if (!this.pageHistory.isEmpty()) {
            final PageMemento current = this.createPageMemento();
            final PageMemento prev = this.pageHistory.remove(this.pageHistory.size() - 1);
            this.pageBackHistory.add(current);
            this.restoreState(prev);
        }
    }

    /** Goes to the next page in the navigation history, if available.
     */
    public void goToNextPage() {
        if (!this.pageBackHistory.isEmpty()) {
            final PageMemento current = this.createPageMemento();
            final PageMemento prev = this.pageBackHistory.remove(this.pageBackHistory.size() - 1);
            this.pageHistory.add(current);
            this.restoreState(prev);
        }
    }

    /** Clears the back navigation history.
     */
    public void clearPageBackHistory() {
        this.pageBackHistory.clear();
    }

    /** Restores the state of the MainController from a PageMemento.
     *
     * @param state The PageMemento containing the state to restore.
     */
    public void restoreState(final PageMemento state) {
        this.course = state.getCourse();
        this.assignment = state.getAssignment();
        this.changePageWithoutChangingHistory(state.getPage());
    }

    /** Refreshes the current page by reloading its content.
     */
    public void refreshCurrentPage() {
        generateHeader();
        content.getChildren().clear();
        this.loadPageURL(currentPage);
        updateButtons();
    }

    /**
     * Changes the page and updates the navigation history.
     *
     * @param page The page to navigate to.
     */
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

    /**
     * Changes the page without modifying the navigation history.
     *
     * @param page The page to navigate to.
     */
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

    /**
     * Loads the FXML page corresponding to the given Page enum.
     *
     * @param page The page to load.
     */
    public void loadPageURL(final Page page) {
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

    /**
     * Loads an FXML page into the content area.
     *
     * @param path The path to the FXML file.
     */
    public void loadPage(final String path) {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            fxmlLoader.setResources(MainView.getBundle());
            final Parent loadPageRoot = fxmlLoader.load();
            final SubController subController = fxmlLoader.getController();
            subController.setMainViewController(this);
            subController.initializeFully();
            content.getChildren().add(loadPageRoot);
        } catch (IOException e) {
            final Label error = new Label();
            error.getStyleClass().add("error");
            error.setText("Error: " + e.getMessage());
            error.setWrapText(true);
            content.getChildren().add(error);
        }
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(final Assignment assignment) {
        this.assignment = assignment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

    public BorderPane getRoot() {
        return root;
    }
}

package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.handlers.UserHandler;
import view.MainView;

/**
 * Controller for the login view.
 */
public class LogInController extends SubController {
    /**  * Text field for user email input.
     */
    @FXML
    private TextField email;
    /**  * Password field for user password input.
     */
    @FXML
    private PasswordField password;
    /**  * Button to initiate the login process.
     */
    @FXML
    private Button loginButton;
    /**  * Hyperlink to navigate to the registration page.
     */
    @FXML
    private Hyperlink registerLink;
    /**  * Label to display error messages during login attempts.
     */
    @FXML
    private Label errorMessage;

    @Override
    public void initializeFully() {
        registerLink.setOnAction(e -> this.getMainController().changePage(Page.REGISTER_PAGE));
        loginButton.setOnAction(e -> login());
    }

    /**     * Attempts to log in the user with the provided email and password.
     * If successful, navigates to the front page; otherwise, displays an error message.
     */
    public void login() {
        final String emailString = email.getText();
        final String passwordString = password.getText();
        try {
            final int id = UserHandler.loginUser(emailString, passwordString);
            if (id > -1) {
                getMainController().changePage(Page.FRONT_PAGE);
            }
            else {
                final String errorText = MainView.getBundle().getString("invalid_login_error");
                errorMessage.setText(errorText);
            }
        }
        catch (Exception e) {
            errorMessage.setText(e.getMessage());
        }
    }
}

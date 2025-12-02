package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.handlers.UserHandler;
import view.MainView;

/**
 * Controller for the registration view.
 */
public class RegisterController extends SubController {
    /** Field for the user's username. */
    @FXML
    private TextField username;
    /** Field for the user's email address. */
    @FXML
    private TextField email;
    /** Field for the user's password. */
    @FXML
    private PasswordField password;
    /** Field to confirm the user's password. */
    @FXML
    private PasswordField confirmPassword;
    /** Button to initiate the registration process. */
    @FXML
    private Button registerButton;
    /** Label to display error messages during registration attempts. */
    @FXML
    private Label errorMessage;

    /** Initializes the controller and sets up event handlers. */
    public void initialize() {
        registerButton.setOnAction(e -> register());
    }

    @Override
    public void initializeFully() {
        // Required due to inheritance, but not needed for this page.
    }

    /**
     * Registers a new user if all input fields are valid.
     */
    public void register() {
        if (isUsernameValid() && isPasswordValid() && isEmailValid()) {
            final String nameString = username.getText();
            final String emailString = email.getText();
            final String passwordString = password.getText();
            try {
                UserHandler.registerUser(nameString, emailString, passwordString);
                getMainController().goToPrevPage();
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        }
    }

    /**
     * Validates the username field.
     *
     * @return true if the username is valid, false otherwise.
     */
    public boolean isUsernameValid() {
        if (username.getText().isEmpty()) {
            final String errorText = MainView.getBundle().getString("username_empty_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }

    /**
     * Validates the password and confirm password fields.
     *
     * @return true if the passwords are valid, false otherwise.
     */
    public boolean isPasswordValid() {
        if (password.getText().length() < 3) {
            final String errorText = MainView.getBundle().getString("password_length_error");
            errorMessage.setText(errorText);
            return false;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            final String errorText = MainView.getBundle().getString("password_mismatch_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }

    /**
     * Validates the email format.
     *
     * @return true if the email is valid, false otherwise.
     */
    public boolean isEmailValid() {
        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            final String errorText = MainView.getBundle().getString("invalid_email_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }
}

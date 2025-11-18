package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.handlers.UserHandler;
import view.MainView;

public class RegisterController extends SubController {
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorMessage;

    public void initialize() {
        registerButton.setOnAction(e -> {
            register();
        });
    }

    @Override
    public void initializeFully() {
        // Required due to inheritance, but not needed for this page.
    }

    public void register() {
        if (isUsernameValid() && isPasswordValid() && isEmailValid()) {
            String _name = username.getText();
            String _email = email.getText();
            String _password = password.getText();
            try {
                UserHandler.registerUser(_name, _email, _password);
                getMainController().goToPrevPage();
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.setText(e.getMessage());
            }
        }
    }

    public boolean isUsernameValid() {
        if (username.getText().isEmpty()) {
            String errorText = MainView.getBundle().getString("username_empty_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }

    public boolean isPasswordValid() {
        if (password.getText().length() < 3) {
            String errorText = MainView.getBundle().getString("password_length_error");
            errorMessage.setText(errorText);
            return false;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            String errorText = MainView.getBundle().getString("password_mismatch_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }

    public boolean isEmailValid() {
        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            String errorText = MainView.getBundle().getString("invalid_email_error");
            errorMessage.setText(errorText);
            return false;
        }
        return true;
    }
}

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.handlers.UserHandler;

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

    }

    public void register() {
        if (isUsernameValid() && isPasswordValid() && isEmailValid()) {
            String _name = username.getText();
            String _email = email.getText();
            String _password = password.getText();
            try {
                UserHandler.registerUser(_name, _email, _password);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.setText(e.getMessage());
            }
        }
    }

    public boolean isUsernameValid() {
        if (username.getText().isEmpty()) {
            errorMessage.setText("Username is empty");
            return false;
        }
        return true;
    }

    public boolean isPasswordValid() {
        if (password.getText().length() < 3) {
            errorMessage.setText("Password must be at least 3 characters long");
            return false;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            errorMessage.setText("Passwords don't match");
            return false;
        }
        return true;
    }

    public boolean isEmailValid() {
        if (!email.getText().contains("@") || !email.getText().contains(".")) {
            errorMessage.setText("Invalid email address");
            return false;
        }
        return true;
    }
}

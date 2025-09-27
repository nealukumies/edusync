package controller;

import enums.Page;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.handlers.UserHandler;

public class LogInController extends SubController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;
    @FXML
    private Label errorMessage;

    @Override
    public void initializeFully() {
        registerLink.setOnAction(e -> {
            this.getMainController().changePage(Page.REGISTER_PAGE);
        });
        loginButton.setOnAction(e -> {
            login();
        });
    }

    public void login() {
        String _email = email.getText();
        String _password = password.getText();
        try {
            int id = UserHandler.loginUser(_email, _password);
            if (id > -1) {
                getMainController().changePage(Page.FRONT_PAGE);
            }
            else {
                errorMessage.setText("Invalid email or password");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText(e.getMessage());
        }
    }
}

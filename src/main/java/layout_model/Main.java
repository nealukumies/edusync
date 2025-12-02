package layout_model;

import javafx.application.Application;
import model.handlers.UserHandler;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        UserHandler.loadAccount();
        Application.launch(MainView.class, args);
    }
}

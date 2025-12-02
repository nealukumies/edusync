package layout_model;

import javafx.application.Application;
import model.handlers.UserHandler;
import view.MainView;

/**
 * Main class to launch the application.
 */
public class Main {
    /** Main method to start the application.
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        UserHandler.loadAccount();
        Application.launch(MainView.class, args);
    }
}

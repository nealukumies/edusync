package layout_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.handlers.UserHandler;
import view.MainView;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        UserHandler.loadAccount();
        MainView.launch(MainView.class);
    }
}

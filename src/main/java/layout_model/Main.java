package layout_model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.handlers.UserHandler;
import view.MainView;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        UserHandler.loginUser("katti@matikainen.fi", "salasana");
        MainView.launch(MainView.class);
    }
}

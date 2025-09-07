import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarTestApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Calendar2 calendar2 = new Calendar2();
        Scene scene = new Scene(calendar2);
        stage.setScene(scene);
        stage.show();
    }
}

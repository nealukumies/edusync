import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarTestApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CalendarModern calendar = new CalendarModern();
        Scene scene = new Scene(calendar);
        stage.setScene(scene);
        stage.show();
    }
}

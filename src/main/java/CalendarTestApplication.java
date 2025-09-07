import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarTestApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Calendar calendar = new Calendar();
        Scene scene = new Scene(calendar);
        stage.setScene(scene);
        stage.show();
    }
}

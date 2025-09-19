package view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load fonts
        Font roboto = Font.loadFont(getClass().getResource("/font/RobotoSerif_28pt-Regular.ttf").toExternalForm(), 10);
        System.out.println(roboto.getFamily());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainLayout.fxml"));
        Parent root = fxmlLoader.load();

        stage.setTitle("EduSync");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

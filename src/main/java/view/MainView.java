package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainView extends Application {
    private static ResourceBundle bundle;

    @Override
    public void start(Stage stage) throws Exception {

        //Locale locale = new Locale("en", "US");
        //Locale locale = new Locale("uk", "UA");
        Locale locale = new Locale("ar", "DZ");
        bundle = ResourceBundle.getBundle("Messages", locale);

        // Load fonts
        Font roboto = Font.loadFont(
                getClass()
                        .getResource("/font/RobotoSerif_28pt-Regular.ttf")
                        .toExternalForm(),
                10
        );

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/view/MainLayout.fxml"), bundle
        );
        Parent root = fxmlLoader.load();

        if (Screen.getPrimary().getBounds() != null) {
            Rectangle2D bounds = Screen.getPrimary().getBounds();
            stage.setWidth(bounds.getWidth() * 0.9);
            stage.setHeight(bounds.getHeight() * 0.9);
        }

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() < 1600) {
                if (!root.getStyleClass().contains("small-scale")) {
                    root.getStyleClass().add("small-scale");
                }
            } else {
                root.getStyleClass().remove("small-scale");
            }
        });

        stage.setTitle("EduSync");
        //stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    // Load fonts
    Font roboto = Font.loadFont(
      getClass()
        .getResource("/font/RobotoSerif_28pt-Regular.ttf")
        .toExternalForm(),
      10
    );
    //System.out.println(roboto.getFamily());

    FXMLLoader fxmlLoader = new FXMLLoader(
      getClass().getResource("/view/MainLayout.fxml")
    );
    Parent root = fxmlLoader.load();

    if (Screen.getPrimary().getBounds() != null) {
      Rectangle2D bounds = Screen.getPrimary().getBounds();
      System.out.println(bounds.getWidth());
      System.out.println(bounds.getHeight());
      if (bounds.getWidth() < 1610 || bounds.getHeight() < 910) {
        stage.setFullScreen(true);
      }
    }

    stage.setTitle("EduSync");
    stage.setResizable(false);
    stage.setScene(new Scene(root, 1536, 864));
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

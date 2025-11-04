package view;

import enums.Language;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainView extends Application {
    private static ResourceBundle bundle;
    private static Language currentLanguage;
    private static Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        if (!getSavedLanguage()) {
            setLanguage(Language.ENGLISH);
        }

        // Load fonts
        Font roboto = Font.loadFont(getClass().getResource("/font/RobotoSerif_28pt-Regular.ttf").toExternalForm(), 10);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"), bundle);
        root = fxmlLoader.load();

        updateOrientation();

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

    public static boolean getSavedLanguage() {
        String filePath = "language.txt";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String fullcode = br.readLine();
            setLanguage(Language.getLanguage(fullcode));
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void saveLanguage() {
        String filePath = "language.txt";

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write((currentLanguage.getCode() + "-" + currentLanguage.getCountry()).getBytes());
            fos.close();

        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }

    }

    public static void setLanguage(Language lang) {
        currentLanguage = lang;
        String code = lang.getCode();
        String country = lang.getCountry();
        Locale currentLocale = new Locale(code, country);
        bundle = ResourceBundle.getBundle("Messages", currentLocale);
        saveLanguage();
        if (root != null) {
            updateOrientation();
        }
    }

    public static void updateOrientation() {
        if (currentLanguage.isReverseOrientation()) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

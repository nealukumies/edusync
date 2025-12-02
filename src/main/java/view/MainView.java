package view;

import enums.Language;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main application
 */
public class MainView extends Application {
    /**
     * ResourceBundle that holds localization strings
     * Updated according to current language
     */
    private static ResourceBundle bundle;
    /**
     * Enum reference representing the current language
     */
    private static Language currentLanguage;
    /**
     * Reference to the root element of the JavaFX application
     */
    private static Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        if (!getSavedLanguage()) {
            setLanguage(Language.ENGLISH);
        }

        final String SMALL_SCALE = "small_scale";

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"), bundle);
        setRoot(fxmlLoader.load());

        updateOrientation();


        if (Screen.getPrimary().getBounds() != null) {
            final double SCREEN_SCALE = 0.9;
            Rectangle2D bounds = Screen.getPrimary().getBounds();
            stage.setWidth(bounds.getWidth() * SCREEN_SCALE);
            stage.setHeight(bounds.getHeight() * SCREEN_SCALE);
        }

        final int SCREEN_WIDTH_BREAKPOINT = 1600;
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() < SCREEN_WIDTH_BREAKPOINT) {
                if (!MainView.root.getStyleClass().contains(SMALL_SCALE)) {
                    MainView.root.getStyleClass().add(SMALL_SCALE);
                }
            } else {
                MainView.root.getStyleClass().remove(SMALL_SCALE);
            }
        });

        stage.setTitle("EduSync");
        stage.setScene(new Scene(MainView.root));
        stage.show();
    }

    /**
     * Static setter for the root to avoid writing static fields directly from instance methods.
     */
    private static void setRoot(Parent newRoot) {
        root = newRoot;
    }

    /**
     * Loads saved language from file
     * <p>
     * If no saved language is found, defaults to English
     * Returns true if language was loaded successfully, false otherwise
     *
     * @return
     */
    public static boolean getSavedLanguage() {
        String filePath = "language.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String fullcode = br.readLine();
            setLanguage(Language.getLanguage(fullcode));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new LanguageException("Failed to read saved language", e);
        }
    }

    /**
     * Saves current language select to file
     */
    public static void saveLanguage() {
        String filePath = "language.txt";
        byte[] data = (currentLanguage.getCode() + "-" + currentLanguage.getCountry())
                .getBytes(java.nio.charset.StandardCharsets.UTF_8);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to save language", e);
        }
    }

    /**
     * Changes current language to new selection
     *
     * @param lang New language enum reference
     */
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

    /**
     * Swaps the app from left-to-right to right-to-left or vice versa
     */
    public static void updateOrientation() {
        if (currentLanguage.getIsRTL()) {
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

    /**
     * Launches the app
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

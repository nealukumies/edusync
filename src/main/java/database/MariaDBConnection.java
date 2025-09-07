package database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBConnection {

    private static Connection conn = null;
    private static Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        System.out.println("URL=" + url);
        if (conn==null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
        } return conn;
    }

    public static void main(String[] args) {
        try (Connection conn = MariaDBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("WRONG");
            e.printStackTrace();
        }
    }
}

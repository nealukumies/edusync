package model;

import io.github.cdimascio.dotenv.Dotenv;
import model.Authetication.Account;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {
    private static Account acc;
    private static HttpClient client;
    private static Connection INSTANCE;
    private static Dotenv dotenv = Dotenv.load();

    private Connection() {
    }

    public static Connection getInstance() {
        if (INSTANCE == null) {
            client = HttpClient.newHttpClient();
            acc = Account.getInstance();
            INSTANCE = new Connection();
        }
        return INSTANCE;
    }

    public HttpResponse<String> sendPostRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(dotenv.get("BACKEND_URL") + endpoint))
                    .header("Content-Type", "application/json")
                    .header("role", acc.getRole())
                    .header("student_id", String.valueOf(acc.getStudentId()))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public  HttpResponse<String> sendPutRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(dotenv.get("BACKEND_URL") + endpoint))
                    .header("Content-Type", "application/json")
                    .header("role", acc.getRole())
                    .header("student_id", String.valueOf(acc.getStudentId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(req))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public HttpResponse<String> sendDeleteRequest(String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(dotenv.get("BACKEND_URL") + endpoint))
                    .header("Content-Type", "application/json")
                    .header("role", acc.getRole())
                    .header("student_id", String.valueOf(acc.getStudentId()))
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

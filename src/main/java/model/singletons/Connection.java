package model.singletons;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {
    private static Account acc;
    private static HttpClient client;
    private static Connection instance;
    private static final Dotenv dotenv = Dotenv.load();

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String ROLE = "role";
    private static final String STUDENT_ID = "student_id";

    public String getBackendUrl() {
        return dotenv.get("BACKEND_URL");
    }

    private Connection() {
    }

    public static Connection getInstance() {
        if (instance == null) {
            client = HttpClient.newHttpClient();
            acc = Account.getInstance();
            instance = new Connection();
        }
        return instance;
    }

    public HttpResponse<String> sendGetRequest(String endpoint) {
        HttpResponse<String> response = null;

        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new IllegalStateException("Failed to send request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return response;
    }

    public HttpResponse<String> sendPostRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new IllegalStateException("Failed to post request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return response;
    }

    public HttpResponse<String> sendPutRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .PUT(HttpRequest.BodyPublishers.ofString(req))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new IllegalStateException("Failed to put request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return response;
    }

    public HttpResponse<String> sendDeleteRequest(String endpoint) {
        HttpResponse<String> response = null;

        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new IllegalStateException("Failed to send delete request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return response;
    }
}

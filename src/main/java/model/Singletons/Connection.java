package model.Singletons;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {
    private static Account acc;
    private static HttpClient client;
    private static Connection INSTANCE;
    private static Dotenv dotenv = Dotenv.load();

    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_TYPE_JSON = "application/json";
    private final String ROLE = "role";
    private final String STUDENT_ID = "student_id";

    public String getBackendUrl() {
        return dotenv.get("BACKEND_URL");
    }

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

    public HttpResponse<String> sendGetRequest(String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public HttpResponse<String> sendPostRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public HttpResponse<String> sendPutRequest(String req, String endpoint) {
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
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
                    .uri(URI.create(getBackendUrl() + endpoint))
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .header(ROLE, acc.getRole())
                    .header(STUDENT_ID, String.valueOf(acc.getStudentId()))
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

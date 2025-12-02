package model.singletons;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** * Singleton class for managing HTTP connections to the backend server.
 */
public class Connection {
    /** The account singleton instance.
     */
    private static Account acc;
    /** The HTTP client used for sending requests.
     */
    private static HttpClient client;
    /** The singleton instance of the Connection class.
     */
    private static Connection instance;
    /** Loads environment variables from a .env file.
     */
    private static final Dotenv dotenv = Dotenv.load();

    /** Content type header key.
     */
    private static final String CONTENT_TYPE = "Content-Type";
    /** JSON content type header value.
     */
    private static final String CONTENT_TYPE_JSON = "application/json";
    /** Role header key.
     */
    private static final String ROLE = "role";
    /** Student ID header key.
     */
    private static final String STUDENT_ID = "student_id";

    /** Gets the backend URL from environment variables.
     *
     * @return The backend URL as a String.
     */
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

    /**
     * Sends an HTTP GET request to the specified endpoint.
     *
     * @param endpoint The API endpoint to send the GET request to.
     * @return The HttpResponse received from the server.
     */
    public HttpResponse<String> sendGetRequest(final String endpoint) {
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

    /**
     * Sends an HTTP POST request to the specified endpoint with the given request body.
     *
     * @param req      The request body to be sent.
     * @param endpoint The API endpoint to send the POST request to.
     * @return The HttpResponse received from the server.
     */
    public HttpResponse<String> sendPostRequest(final String req, final String endpoint) {
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

    /**
     * Sends an HTTP PUT request to the specified endpoint with the given request body.
     *
     * @param req      The request body to be sent.
     * @param endpoint The API endpoint to send the PUT request to.
     * @return The HttpResponse received from the server.
     */
    public HttpResponse<String> sendPutRequest(final String req, final String endpoint) {
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

    /**
     * Sends an HTTP DELETE request to the specified endpoint.
     *
     * @param endpoint The API endpoint to send the DELETE request to.
     * @return The HttpResponse received from the server.
     */
    public HttpResponse<String> sendDeleteRequest(final String endpoint) {
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

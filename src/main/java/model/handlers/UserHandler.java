package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.net.http.HttpResponse;

public class UserHandler {
    static public int loginUser(String inputEmail, String inputPassword) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }""", inputEmail, inputPassword);

        HttpResponse<String> response = conn.sendPostRequest(inputString, "/login");

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                int studentId = jsonNode.get("studentId").asInt();
                String name = jsonNode.get("name").asText();
                String email = jsonNode.get("email").asText();
                String role = jsonNode.get("role").asText();

                Account account = Account.getInstance();
                if (!account.setAccountDetails(studentId, name, email, role)) {
                    return -1;
                }

                System.out.println("Successfully logged in to " + jsonNode.get("name"));
                return studentId;
            }
            case 400 -> {
                System.out.println("Bad Request: Missing or invalid fields in the request body.");
                return -1;
            }
            case 401 -> {
                System.out.println("Unauthorized: Incorrect email or password.");
                return -1;
            }
            default -> {
                System.out.println("Error: Received status code " + status);
                return -1;
            }
        }
    }

    public static HttpResponse<String> getUser() {
        Connection conn = Connection.getInstance();

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/students/" + studentId;

        HttpResponse<String> response = conn.sendGetRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                System.out.println("Successfully fetched user data.");
                return response;
            }
            case 403 -> {
                System.out.println("403 Forbidden: Unauthorized access to fetch the student's account.");
            }
            case 404 -> {
                System.out.println("404 Not Found: Student with the specified ID does not exist.");
            }
            default -> {
                System.out.println("Error: Received status code " + status);
            }
        }
        return null;
    }

    static public int updateUser(String inputName, String inputEmail) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
                {
                    "name": "%s",
                    "email": "%s"
                }""", inputName, inputEmail);

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/students/" + studentId;

        HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                Account account = Account.getInstance();
                account.clearAccount();

                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                int Id = jsonNode.get("id").asInt();
                String name = jsonNode.get("name").asText();
                String email = jsonNode.get("email").asText();
                String role = jsonNode.get("role").asText();

                account.setAccountDetails(Id, name, email, role);

                System.out.println("Successfully updated user " + inputName);
                return 1;
            }
            case 400 -> {
                System.out.println("Bad Request: Missing or invalid fields in the request body.");
                return -1;
            }
            case 403 -> {
                System.out.println("Forbidden: Unauthorized access to the student's data.");
                return -1;
            }
            case 404 -> {
                System.out.println("Not Found: Student with the specified ID does not exist.");
                return -1;
            }
            case 409 -> {
                System.out.println("Conflict: Email already in use.");
                return -1;
            }
            default -> {
                System.out.println("Error: Received status code " + status);
                return -1;
            }
        }
    }

    static public int registerUser(String inputName, String inputEmail, String inputPassword) throws JsonProcessingException {
        Connection conn = Connection.getInstance();

        String inputString = String.format("""
            {
                "name": "%s",
                "email": "%s",
                "password": "%s"
            }""", inputName, inputEmail, inputPassword);

        HttpResponse<String> response = conn.sendPostRequest(inputString, "/students");

        int status = response.statusCode();

        switch (status) {
            case 201 -> {
                Account account = Account.getInstance();
                account.clearAccount();

                ObjectMapper mapper = new ObjectMapper();

                JsonNode jsonNode = mapper.readTree(response.body());

                int studentId = jsonNode.get("id").asInt();
                String name = jsonNode.get("name").asText();
                String email = jsonNode.get("email").asText();
                String role = jsonNode.get("role").asText();

                account.setAccountDetails(studentId, name, email, role);

                System.out.println("Successfully registered user " + inputName);
                return 1;
            }
            case 400 -> {
                System.out.println("Bad Request: Missing or invalid fields in the request body.");
                return -1;
            }
            case 409 -> {
                System.out.println("Conflict: Email already in use.");
                return -1;
            }
            default -> {
                System.out.println("Error: Received status code " + status);
                return -1;
            }
        }
    }

    public static int deleteUser() {
        Connection conn = Connection.getInstance();

        int studentId = Account.getInstance().getStudentId();
        String endpoint = "/students/" + studentId;

        HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        int status = response.statusCode();

        switch (status) {
            case 200 -> {
                Account account = Account.getInstance();
                account.clearAccount();

                System.out.println("Successfully deleted user ");
                return 1;
            }
            case 403 -> {
                System.out.println("Forbidden: Unauthorized access to delete the student's account.");
                return -1;
            }
            case 404 -> {
                System.out.println("Not Found: Student with the specified ID does not exist.");
                return -1;
            }
            default -> {
                System.out.println("Error: Received status code " + status);
                return -1;
            }
        }
    }
}

package model.Authetication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Connection;

import java.net.http.HttpResponse;

public class Login {
    static public int tryLogin(String inputEmail, String inputPassword) throws JsonProcessingException {
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
}

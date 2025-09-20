package model.Authetication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Connection;

import java.net.http.HttpResponse;

public class Register {
    static public int tryRegister(String inputName, String inputEmail, String inputPassword) throws JsonProcessingException {
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
}

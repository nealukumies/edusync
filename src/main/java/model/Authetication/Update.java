package model.Authetication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Connection;

import java.net.http.HttpResponse;

public class Update {
    static public int tryUpdate(String inputName, String inputEmail) throws JsonProcessingException {
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
}

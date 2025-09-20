package model.Authetication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Connection;

import java.net.http.HttpResponse;

public class Delete {
    public static int tryDelete() {
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

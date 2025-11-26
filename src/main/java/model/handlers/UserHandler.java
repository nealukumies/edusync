package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.http.HttpResponse;

public class UserHandler {
    static public int loginUser(String inputEmail, String inputPassword) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }""", inputEmail, inputPassword);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/login");

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                final ObjectMapper mapper = new ObjectMapper();

                final JsonNode jsonNode = mapper.readTree(response.body());

                final int studentId = jsonNode.get("studentId").asInt();
                final String name = jsonNode.get("name").asText();
                final String email = jsonNode.get("email").asText();
                final String role = jsonNode.get("role").asText();

                System.out.println(jsonNode.get("studentId").asInt());
                System.out.println(jsonNode.get("name").asText());
                System.out.println(jsonNode.get("email").asText());
                System.out.println(jsonNode.get("role").asText());

                final Account account = Account.getInstance();
                if (!account.setAccountDetails(studentId, name, email, role)) {
                    return -1;
                }

                saveAccount();

                return studentId;
            }
            default -> {
                return -1;
            }
        }
    }

    public static void logoutUser() {
        final Account account = Account.getInstance();
        account.clearAccount();
        removeSavedAccount();
    }

    public static HttpResponse<String> getUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = "/students/" + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                return response;
            }
        }
        return null;
    }

    static public int updateUser(String inputName, String inputEmail) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
                {
                    "name": "%s",
                    "email": "%s"
                }""", inputName, inputEmail);

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = "/students/" + studentId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                final Account account = Account.getInstance();
                account.clearAccount();

                final ObjectMapper mapper = new ObjectMapper();

                final JsonNode jsonNode = mapper.readTree(response.body());

                final int id = jsonNode.get("id").asInt();
                final String name = jsonNode.get("name").asText();
                final String email = jsonNode.get("email").asText();
                final String role = jsonNode.get("role").asText();

                account.setAccountDetails(id, name, email, role);

                return 1;
            }
            default -> {
                return -1;
            }
        }
    }

    static public int registerUser(String inputName, String inputEmail, String inputPassword) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "name": "%s",
                "email": "%s",
                "password": "%s"
            }""", inputName, inputEmail, inputPassword);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/students");

        final int status = response.statusCode();

        switch (status) {
            case 201 -> {
                final Account account = Account.getInstance();
                account.clearAccount();

                final ObjectMapper mapper = new ObjectMapper();

                final JsonNode jsonNode = mapper.readTree(response.body());

                final int studentId = jsonNode.get("id").asInt();
                final String name = jsonNode.get("name").asText();
                final String email = jsonNode.get("email").asText();
                final String role = jsonNode.get("role").asText();

                account.setAccountDetails(studentId, name, email, role);

                return 1;
            }
            default -> {
                return -1;
            }
        }
    }

    public static int deleteUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = "/students/" + studentId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        switch (status) {
            case 200 -> {
                final Account account = Account.getInstance();
                account.clearAccount();

                return 1;
            }
            default -> {
                return -1;
            }
        }
    }

    public static void saveAccount() {
        final Account account = Account.getInstance();
        final String filePath = "account.txt";

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(account);
        } catch (IOException e) {}
    }

    public static void loadAccount() {
        final String filePath = "account.txt";
        final Account account = Account.getInstance();

        try (java.io.FileInputStream fis = new java.io.FileInputStream(filePath);
             java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {

            final Account loadedAccount = (Account) ois.readObject();
            account.setAccountDetails(loadedAccount.getStudentId(), loadedAccount.getName(), loadedAccount.getEmail(), loadedAccount.getRole());

        } catch (Exception e) {}
    }

    public static boolean removeSavedAccount() {
        final java.io.File file = new java.io.File("account.txt");
        try {
            return file.delete();
        } catch (SecurityException e) {
            throw new IllegalStateException("Failed to delete saved account file", e);
        }
    }
}

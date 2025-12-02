package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Singletons.Account;
import model.Singletons.Connection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.http.HttpResponse;

public class UserHandler {
    static final String STUDENTS_STRING = "/students/";
    static final String EMAIL_STRING = "email";
    static final String ID_STRING = "id";
    static final String NAME_STRING = "name";
    static final String ROLE_STRING = "role";
    static final String ACCOUNT_STRING = "account.txt";

    private UserHandler() {}

    public static int loginUser(String inputEmail, String inputPassword) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }""", inputEmail, inputPassword);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/login");

        final int status = response.statusCode();

        if (status == 200) {
            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            final int studentId = jsonNode.get("studentId").asInt();
            final String name = jsonNode.get(NAME_STRING).asText();
            final String email = jsonNode.get(EMAIL_STRING).asText();
            final String role = jsonNode.get(ROLE_STRING).asText();

            final Account account = Account.getInstance();
            if (!account.setAccountDetails(studentId, name, email, role)) {
                return -1;
            }

            saveAccount();

            return studentId;
        }
        return -1;
    }

    public static void logoutUser() {
        final Account account = Account.getInstance();
        account.clearAccount();
        removeSavedAccount();
    }

    public static HttpResponse<String> getUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = STUDENTS_STRING + studentId;

        final HttpResponse<String> response = conn.sendGetRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            return response;
        }
        return null;
    }

    public static int updateUser(String inputName, String inputEmail) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
                {
                    "name": "%s",
                    "email": "%s"
                }""", inputName, inputEmail);

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = STUDENTS_STRING + studentId;

        final HttpResponse<String> response = conn.sendPutRequest(inputString, endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            final Account account = Account.getInstance();
            account.clearAccount();

            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            final int id = jsonNode.get(ID_STRING).asInt();
            final String name = jsonNode.get(NAME_STRING).asText();
            final String email = jsonNode.get(EMAIL_STRING).asText();
            final String role = jsonNode.get(ROLE_STRING).asText();

            account.setAccountDetails(id, name, email, role);

            return 1;
        }
        return -1;
    }

    public static int registerUser(String inputName, String inputEmail, String inputPassword) throws JsonProcessingException {
        final Connection conn = Connection.getInstance();

        final String inputString = String.format("""
            {
                "name": "%s",
                "email": "%s",
                "password": "%s"
            }""", inputName, inputEmail, inputPassword);

        final HttpResponse<String> response = conn.sendPostRequest(inputString, "/students");

        final int status = response.statusCode();

        if (status == 201) {
            final Account account = Account.getInstance();
            account.clearAccount();

            final ObjectMapper mapper = new ObjectMapper();

            final JsonNode jsonNode = mapper.readTree(response.body());

            final int studentId = jsonNode.get(ID_STRING).asInt();
            final String name = jsonNode.get(NAME_STRING).asText();
            final String email = jsonNode.get(EMAIL_STRING).asText();
            final String role = jsonNode.get(ROLE_STRING).asText();

            account.setAccountDetails(studentId, name, email, role);

            return 1;
        }
        return -1;
    }

    public static int deleteUser() {
        final Connection conn = Connection.getInstance();

        final int studentId = Account.getInstance().getStudentId();
        final String endpoint = STUDENTS_STRING + studentId;

        final HttpResponse<String> response = conn.sendDeleteRequest(endpoint);

        final int status = response.statusCode();

        if (status == 200) {
            final Account account = Account.getInstance();
            account.clearAccount();

            return 1;
        }
        return -1;
    }

    public static void saveAccount() {
        final Account account = Account.getInstance();

        try (FileOutputStream fos = new FileOutputStream(ACCOUNT_STRING);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(account);
        } catch (IOException ignored) {
            //block empty to ignore errors
        }
    }

    public static void loadAccount() {
        final Account account = Account.getInstance();

        try (java.io.FileInputStream fis = new java.io.FileInputStream(ACCOUNT_STRING);
             java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis)) {

            final Account loadedAccount = (Account) ois.readObject();
            account.setAccountDetails(loadedAccount.getStudentId(), loadedAccount.getName(), loadedAccount.getEmail(), loadedAccount.getRole());

        } catch (Exception e) {
            //block empty to ignore errors
        }
    }

    public static void removeSavedAccount() {
        final java.nio.file.Path path = java.nio.file.Paths.get(ACCOUNT_STRING);
        try {
            java.nio.file.Files.delete(path);
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Failed to delete saved account file", e);
        } catch (SecurityException e) {
            throw new IllegalStateException("Security manager prevented deletion of saved account file: " + path, e);
        }
    }
}

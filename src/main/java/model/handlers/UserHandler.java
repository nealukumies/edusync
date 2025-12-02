package model.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.singletons.Account;
import model.singletons.Connection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.http.HttpResponse;

/** Handler class for user-related operations such as login, logout, registration, and account management.
 */
public class UserHandler {
    /** Students string for HTTP endpoints.
     */
    static final String STUDENTS_STRING = "/students/";
    /** Email string for HTTP headers and JSON keys.
     */
    static final String EMAIL_STRING = "email";
    /** ID string for HTTP headers and JSON keys.
     */
    static final String ID_STRING = "id";
    /** Name string for HTTP headers and JSON keys.
     */
    static final String NAME_STRING = "name";
    /** Role string for HTTP headers and JSON keys.
     */
    static final String ROLE_STRING = "role";
    /** Filename for storing account information.
     */
    static final String ACCOUNT_STRING = "account.txt";

    private UserHandler() {}

    /** Logs in a user with the provided email and password.
     *
     * @param inputEmail    The email of the user.
     * @param inputPassword The password of the user.
     * @return The student ID if login was successful, -1 otherwise.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static int loginUser(final String inputEmail, final String inputPassword) throws JsonProcessingException {
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

    /** Logs out the current user by clearing the account details and removing the saved account file.
     */
    public static void logoutUser() {
        final Account account = Account.getInstance();
        account.clearAccount();
        removeSavedAccount();
    }

    /** Retrieves the current user's details from the backend.
     *
     * @return The HttpResponse containing the user's details if successful, null otherwise.
     */
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

    /** Updates the current user's details in the backend.
     *
     * @param inputName  The new name of the user.
     * @param inputEmail The new email of the user.
     * @return 1 if the update was successful, -1 otherwise.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static int updateUser(final String inputName, final String inputEmail) throws JsonProcessingException {
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

    /** Registers a new user in the backend.
     *
     * @param inputName     The name of the user.
     * @param inputEmail    The email of the user.
     * @param inputPassword The password of the user.
     * @return 1 if the registration was successful, -1 otherwise.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
    public static int registerUser(final String inputName, final String inputEmail, final String inputPassword) throws JsonProcessingException {
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

    /** Deletes the current user from the backend.
     *
     * @return 1 if the deletion was successful, -1 otherwise.
     */
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

    /** Saves the current account to the filesystem.
     */
    public static void saveAccount() {
        final Account account = Account.getInstance();

        try (FileOutputStream fos = new FileOutputStream(ACCOUNT_STRING);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(account);
        } catch (IOException ignored) {
            //block empty to ignore errors
        }
    }

    /**
     * Loads the saved account from the filesystem.
     */
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

    /**
     * Removes the saved account file from the filesystem.
     */
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

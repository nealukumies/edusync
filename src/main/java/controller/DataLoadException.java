package controller;

public class DataLoadException extends RuntimeException {
    public DataLoadException(final String message) {
        super(message);
    }

    public DataLoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
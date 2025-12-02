package controller;

/**
 * Exception thrown when there is an error loading data.
 */
public class DataLoadException extends RuntimeException {
    /**
     * Constructs a new DataLoadException with the specified detail message.
     *
     * @param message the detail message.
     */
    public DataLoadException(final String message) {
        super(message);
    }

    /**
     * Constructs a new DataLoadException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public DataLoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
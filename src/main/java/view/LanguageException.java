package view;

/**
 * Exception thrown when there is an error related to language processing.
 */
public class LanguageException extends RuntimeException {
    /**
     * Constructs a new LanguageException with the specified detail message.
     *
     * @param message the detail message.
     */
    public LanguageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
package model.calendar;

/** * Exception thrown when there is an error related to calendar data.
 */
public class CalendarDataException extends RuntimeException {
    /**
     * Constructs a new CalendarDataException with the specified detail message.
     *
     * @param message the detail message.
     */
    public CalendarDataException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
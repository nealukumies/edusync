package model.enums;

import view.MainView;

/** Enumeration representing the status of an assignment. */
public enum Status {
    PENDING("pending"),
    IN_PROGRESS("in-progress"),
    COMPLETED("completed"),
    OVERDUE("overdue");

    /** The database value associated with the status. */
    private final String dbValue;
    Status(String dbValue) { this.dbValue = dbValue; }
    public String getDbValue() { return dbValue; }

    /** Convert a database value to a Status enum.
     *
     * @param dbValue The database value as a string.
     * @return The corresponding Status enum.
     * @throws IllegalArgumentException if the dbValue does not match any Status.
     */
    public static Status fromDbValue(final String dbValue) {
        for (final Status s : Status.values()) {
            if (s.getDbValue().equals(dbValue)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + dbValue);
    }

    @Override
    public String toString() {
        switch (this) {
            case PENDING:
                return MainView.getBundle().getString("PENDING");
            case IN_PROGRESS:
                return MainView.getBundle().getString("IN_PROGRESS");
            case COMPLETED:
                return MainView.getBundle().getString("COMPLETED");
            case OVERDUE:
                return MainView.getBundle().getString("OVERDUE");
            default:
                return "";
        }
    }
}

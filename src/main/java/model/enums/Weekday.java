package model.enums;

import view.MainView;

/** Enum representing the days of the week with methods for database conversion. */
public enum Weekday {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    /** The database value associated with the weekday. */
    private final String dbValue;
    Weekday() {
        this.dbValue = this.name().toLowerCase();
    }
    /**
     * Converts a database string value to its corresponding enum constant.
     *
     * @param dbValue the string value from the database
     * @return the corresponding Weekday enum constant
     */
    public static Weekday fromString(final String dbValue) {
        return Weekday.valueOf(dbValue.toUpperCase());
    }

    /**
     * Converts the enum constant to its corresponding database string value.
     *
     * @return the lowercase string representation of the enum constant
     */
    public String toDbValue() {
        return this.name().toLowerCase();
    }

    /** Gets the database value associated with the weekday.
     *
     * @return The database value as a string.
     */
    public String getDbValue() {
        return dbValue;
    }

    @Override
    public String toString() {
        switch (this) {
            case MONDAY:
                return MainView.getBundle().getString("MONDAY");
            case TUESDAY:
                return MainView.getBundle().getString("TUESDAY");
            case WEDNESDAY:
                return MainView.getBundle().getString("WEDNESDAY");
            case THURSDAY:
                return MainView.getBundle().getString("THURSDAY");
            case FRIDAY:
                return MainView.getBundle().getString("FRIDAY");
            case SATURDAY:
                return MainView.getBundle().getString("SATURDAY");
            case SUNDAY:
                return MainView.getBundle().getString("SUNDAY");
            default:
                return "";
        }
    }
}

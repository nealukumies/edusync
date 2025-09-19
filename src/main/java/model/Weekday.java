package model;

public enum Weekday {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static Weekday fromString(String dbValue) {
        return Weekday.valueOf(dbValue.toUpperCase());
    }

    public String toDbValue() {
        return this.name().toLowerCase();
    }
}

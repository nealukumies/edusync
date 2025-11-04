package enums;

public enum Language {
    ARABIC("ar", "DZ", "Arabic"),
    ENGLISH("en", "US", "English"),
    UKRAINIAN("uk", "UA", "Ukrainian");

    private final String code;
    private final String country;
    private final String displayName;

    Language(String code, String country, String displayName) {
        this.code = code;
        this.country = country;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

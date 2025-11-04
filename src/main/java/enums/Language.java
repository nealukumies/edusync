package enums;

public enum Language {
    ARABIC("ar", "DZ", "Arabic", true),
    ENGLISH("en", "US", "English", false),
    UKRAINIAN("uk", "UA", "Ukrainian", false);

    private final String code;
    private final String country;
    private final String displayName;
    private final boolean reverseOrientation;

    Language(String code, String country, String displayName, boolean reverseOrientation) {
        this.code = code;
        this.country = country;
        this.displayName = displayName;
        this.reverseOrientation = reverseOrientation;
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
    
    public boolean isReverseOrientation() {
        return reverseOrientation;
    }

    public static Language getLanguage(String fullcode) {
        String code = fullcode.split("-")[0];
        String country = fullcode.split("-")[1];
        for (Language language : Language.values()) {
            if (language.code.equals(code) && language.country.equals(country)) {
                return language;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

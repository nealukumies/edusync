package enums;

/**
 * Enum for language/localization data
 * Each language has a code, country, display name and orientation flag
 */
public enum Language {
    ARABIC("ar", "DZ", "Arabic", true),
    ENGLISH("en", "US", "English", false),
    UKRAINIAN("uk", "UA", "Ukrainian", false);

    /**
     * Language code
     */
    private final String code;
    /**
     * ISO country code
     */
    private final String country;
    /**
     * Language name in english
     */
    private final String displayName;
    /**
     * For languages that are read from right to left, swaps orientation
     */
    private final boolean isRTL;


    Language(String code, String country, String displayName, boolean isRTL) {
        this.code = code;
        this.country = country;
        this.displayName = displayName;
        this.isRTL = isRTL;
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

    public boolean getIsRTL() {
        return isRTL;
    }

    /**
     * Returns language enum value by its code
     *
     * @param fullcode language-Country (eg. fi-FI)
     * @return
     */
    public static Language getLanguage(String fullcode) {
        final String code = fullcode.split("-")[0];
        final String country = fullcode.split("-")[1];
        for (final Language language : Language.values()) {
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

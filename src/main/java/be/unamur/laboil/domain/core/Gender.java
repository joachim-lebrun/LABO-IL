package be.unamur.laboil.domain.core;

/**
 * @author Joachim Lebrun on 12-08-18
 */
public enum Gender {

    MALE("M"), FEMALE("F");

    private final String abbr;

    Gender(String abbr) {
        this.abbr = abbr;
    }

    public static Gender fromAbbr(String value) {
        if ("m".equalsIgnoreCase(value)) return MALE;
        else return FEMALE;
    }

    public String getAbbr() {
        return abbr;
    }
}

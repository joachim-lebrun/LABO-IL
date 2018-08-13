package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode
public class Town {

    private String townID;
    private String name;
    private String language;
    private String country;
    private int postalCode;

    public Town() {
    }

    @Builder
    public Town(String townID, String name, String language, int postalCode, String country) {
        this.townID = townID;
        this.name = name;
        this.language = language;
        this.postalCode = postalCode;
        this.country = country;
    }
}

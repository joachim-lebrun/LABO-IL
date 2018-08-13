package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class TownDAO {
    private String townID;
    private String name;
    private String language;
    private int postalCode;
    private String country;

    @Builder
    public TownDAO(String townID, String name, String language, int postalCode, String country) {
        this.townID = townID;
        this.name = name;
        this.language = language;
        this.postalCode = postalCode;
        this.country = country;
    }
}

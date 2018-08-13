package be.unamur.laboil.domain.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */

@Getter
@Setter
public class TownView {
    private String name;
    private String language;
    private int postalCode;
    private String country;

    public TownView() {

    }

    @Builder
    public TownView(String name, String language, int postalCode, String country) {
        this.name = name;
        this.language = language;
        this.postalCode = postalCode;
        this.country = country;
    }
}

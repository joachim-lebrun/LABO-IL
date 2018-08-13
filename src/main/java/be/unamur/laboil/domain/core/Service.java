package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode
public class Service {

    private String serviceID;
    private String name;
    private Town town;

    public Service() {
    }

    @Builder

    public Service(String serviceID, String name, Town town) {
        this.serviceID = serviceID;
        this.name = name;
        this.town = town;
    }
}

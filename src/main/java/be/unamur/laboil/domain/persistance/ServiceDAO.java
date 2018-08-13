package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class ServiceDAO {
    private String serviceID;
    private String name;
    private String town;

    @Builder
    public ServiceDAO(String serviceID, String name, String town) {
        this.serviceID = serviceID;
        this.name = name;
        this.town = town;
    }
}

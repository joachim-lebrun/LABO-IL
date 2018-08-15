package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TownDAO {

    private String townID;
    private String name;
    private String language;
    private String address;
    private String phoneNumber;
    private String email;
    private int postalCode;
    private String country;
    private String logoPath;

}

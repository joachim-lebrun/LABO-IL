package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Town {

    private String townID;
    private String name;
    private String language;
    private String address;
    private String email;
    private String phoneNumber;
    private Employee mayor;
    private File logo;
    private String country;
    private int postalCode;
}

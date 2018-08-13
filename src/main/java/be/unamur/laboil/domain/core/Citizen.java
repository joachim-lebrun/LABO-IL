package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User {

    private Town town;

    public Citizen() {
    }

    @Builder

    public Citizen(String userID, String firstName, String lastName, String address, String phoneNumber, LocalDate birthDate, String email, String password, boolean enabled, String natReg, Gender gender, String birthPlace, Town town) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, password, enabled, natReg, gender, birthPlace);
        this.town = town;
    }
}



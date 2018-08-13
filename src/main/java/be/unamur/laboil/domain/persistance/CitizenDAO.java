package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class CitizenDAO extends UserDAO {

    private String town;

    @Builder

    public CitizenDAO(String userID, String firstName, String lastName, String address, String birthDate, String email, String password, String natReg, String gender, String phoneNumber, String birthPlace, boolean enabled, String town) {
        super(userID, firstName, lastName, address, birthDate, email, password, natReg, gender, phoneNumber, birthPlace, enabled);
        this.town = town;
    }
}

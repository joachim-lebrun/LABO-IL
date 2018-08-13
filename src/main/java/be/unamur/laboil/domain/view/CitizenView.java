package be.unamur.laboil.domain.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class CitizenView extends UserView {


    private String town;

    public CitizenView() {
    }

    @Builder

    public CitizenView(String userID, String firstName, String lastName, String address, String phoneNumber, String birthDate, String email, String gender, String birthPlace, String password, boolean enabled, String natReg, String town) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, gender, birthPlace, password, enabled, natReg);
        this.town = town;
    }
}

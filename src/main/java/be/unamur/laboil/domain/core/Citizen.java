package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User {

    private Town town;
    private String citizenID;

    public Citizen() {
    }

    @Builder

    public Citizen(String userID, String firstName, String lastName, String address, String phoneNumber, LocalDate birthDate, String email, String password, boolean enabled, String natReg, Town town, String citizenID) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, password, enabled, natReg);
        this.town = town;
        this.citizenID = citizenID;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("citizenID", citizenID)
                .toString();
    }
}



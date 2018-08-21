package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class CitizenDAO extends UserDAO {

    private String town;
    private String citizenID;


    @Builder
    public CitizenDAO(String userID, String firstName, String lastName, String address, String birthDate, String email, String password, String natReg, String phoneNumber, boolean enabled, String town, String citizenID) {
        super(userID, firstName, lastName, address, birthDate, email, password, natReg, phoneNumber, enabled);
        this.town = town;
        this.citizenID = citizenID;
    }

    public CitizenDAO() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("town", town)
                .append("citizenID", citizenID)
                .toString();
    }
}

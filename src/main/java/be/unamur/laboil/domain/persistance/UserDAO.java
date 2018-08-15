package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserDAO {

    private String userID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private String email;
    private String password;
    private String natReg;
    private String phoneNumber;
    private boolean enabled;

}

package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String gender;
    private String phoneNumber;
    private String birthPlace;
    private boolean enabled;

}

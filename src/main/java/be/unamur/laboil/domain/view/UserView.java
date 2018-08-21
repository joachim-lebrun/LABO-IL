package be.unamur.laboil.domain.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserView {
    private String userID;

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String birthDate;
    private String email;
    private String password;
    private boolean enabled;
    private String natReg;

}

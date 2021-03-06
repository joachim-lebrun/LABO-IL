package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private boolean enabled;
    private String natReg;

    public String getDisplayName() {
        return String.format("%s %s", lastName, firstName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .toString();
    }
}

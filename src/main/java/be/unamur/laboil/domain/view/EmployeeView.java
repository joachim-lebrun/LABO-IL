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
public class EmployeeView extends UserView {
    private String role;
    private String service;

    public EmployeeView() {

    }

    @Builder

    public EmployeeView(String userID, String firstName, String lastName, String address, String phoneNumber, String birthDate, String email, String gender, String birthPlace, String password, boolean enabled, String natReg, String role, String service) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, gender, birthPlace, password, enabled, natReg);
        this.role = role;
        this.service = service;
    }
}

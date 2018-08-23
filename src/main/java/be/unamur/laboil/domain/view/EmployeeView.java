package be.unamur.laboil.domain.view;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class EmployeeView extends UserView {
    private String service;

    public EmployeeView() {

    }

    @Builder
    public EmployeeView(String userID, String firstName, String lastName, String address, String phoneNumber, String birthDate, String email, String password, boolean enabled, String natReg, String role, String service) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, password, enabled, natReg);
        this.service = service;
    }
}

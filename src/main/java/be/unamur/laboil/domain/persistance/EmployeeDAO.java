package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class EmployeeDAO extends UserDAO {

    private String employeeID;
    private String service;

    @Builder
    public EmployeeDAO(String userID, String firstName, String lastName, String address, String birthDate, String email, String password, String natReg, String phoneNumber, boolean enabled, String employeeID, String service) {
        super(userID, firstName, lastName, address, birthDate, email, password, natReg, phoneNumber, enabled);
        this.employeeID = employeeID;
        this.service = service;
    }

    public EmployeeDAO() {
    }
}

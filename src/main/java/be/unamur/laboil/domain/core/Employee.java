package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    private Service service;
    private String empoyeeID;

    @Builder
    public Employee(String userID, String firstName, String lastName, String address, String phoneNumber, LocalDate birthDate, String email, String password, boolean enabled, String natReg, Service service, String empoyeeID) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, password, enabled, natReg);
        this.service = service;
        this.empoyeeID = empoyeeID;
    }
}


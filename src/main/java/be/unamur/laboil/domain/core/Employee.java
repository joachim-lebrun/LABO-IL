package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    private Service service;
    private String role;

    @Builder

    public Employee(String userID, String firstName, String lastName, String address, String phoneNumber, LocalDate birthDate, String email, String password, boolean enabled, String natReg, Gender gender, String birthPlace, Service service, String role) {
        super(userID, firstName, lastName, address, phoneNumber, birthDate, email, password, enabled, natReg, gender, birthPlace);
        this.service = service;
        this.role = role;
    }
}


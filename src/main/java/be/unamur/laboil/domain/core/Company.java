package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode
public class Company {

    private String name;
    private String address;
    private String vatNumber;
    private String phone;
    private Town town;

    public Company() {
    }

    @Builder

    public Company(String name, String address, String vatNumber, String phone, Town town) {
        this.name = name;
        this.address = address;
        this.vatNumber = vatNumber;
        this.phone = phone;
        this.town = town;
    }
}




package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;



/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data

public class CompanyDAO{
    private String name;
    private String address;
    private String vatNumber;
    private String town;
    private String phone;


    @Builder
    public CompanyDAO(String name, String address, String vatNumber, String town,  String phone) {
        this.name = name;
        this.address = address;
        this.vatNumber = vatNumber;
        this.town = town;
        this.phone = phone;
    }
}




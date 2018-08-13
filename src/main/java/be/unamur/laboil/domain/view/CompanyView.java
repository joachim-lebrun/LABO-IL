package be.unamur.laboil.domain.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */

@Getter
@Setter
public class CompanyView  {
    private String companyName;
    private String companyAddress;
    private String companyVATNumber;
    private String town;
    private String companyPhone;

    public CompanyView() {
    }

    @Builder
    public CompanyView( String companyName, String companyAddress, String companyVATNumber, String town, String companyPhone) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyVATNumber = companyVATNumber;
        this.town = town;
        this.companyPhone = companyPhone;
    }
}

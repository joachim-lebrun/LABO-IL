package be.unamur.laboil.domain.view.form;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class OfficialDocumentForm {

    private LocalDate documentDate;
    private String documentID;
    private String documentBeneficiary;
    private String documentIssuerService;
    private String documentIssuerTown;
    private String documentTracking;
    private LocalDate documentExpiration;

    public OfficialDocumentForm(){

    }

    @Builder
    public OfficialDocumentForm(LocalDate documentDate, String documentID, String documentBeneficiary, String documentIssuerService, String documentIssuerTown, String documentTracking, LocalDate documentExpiration) {
        this.documentDate = documentDate;
        this.documentID = documentID;
        this.documentBeneficiary = documentBeneficiary;
        this.documentIssuerService = documentIssuerService;
        this.documentIssuerTown = documentIssuerTown;
        this.documentTracking = documentTracking;
        this.documentExpiration = documentExpiration;
    }
}

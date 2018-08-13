package be.unamur.laboil.domain.view;



import java.time.LocalDate;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class OfficialDocumentView {

    private LocalDate documentDate;
    private String documentID;
    private String documentBeneficiary;
    private String documentIssuerService;
    private String documentIssuerTown;
    private String documentTracking;
    private LocalDate documentExpiration;

    public OfficialDocumentView(){

    }

    @Builder
    public OfficialDocumentView(LocalDate documentDate, String documentID, String documentBeneficiary, String documentIssuerService, String documentIssuerTown, String documentTracking, LocalDate documentExpiration) {
        this.documentDate = documentDate;
        this.documentID = documentID;
        this.documentBeneficiary = documentBeneficiary;
        this.documentIssuerService = documentIssuerService;
        this.documentIssuerTown = documentIssuerTown;
        this.documentTracking = documentTracking;
        this.documentExpiration = documentExpiration;
    }
}

package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class OfficialDocumentDAO {
    private LocalDate creationDate;
    private String documentID;
    private String beneficiary;
    private String issuerService;
    private String issuerTown;
    private String tracking;
    private LocalDate expirationDate;

    @Builder
    public OfficialDocumentDAO(LocalDate creationDate, String documentID, String beneficiary, String issuerService, String issuerTown, String tracking, LocalDate expirationDate) {
        this.creationDate = creationDate;
        this.documentID = documentID;
        this.beneficiary = beneficiary;
        this.issuerService = issuerService;
        this.issuerTown = issuerTown;
        this.tracking = tracking;
        this.expirationDate = expirationDate;
    }
}

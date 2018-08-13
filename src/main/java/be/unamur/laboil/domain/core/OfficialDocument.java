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
@EqualsAndHashCode
public class OfficialDocument {

    private LocalDate creationDate;
    private String documentID;

    private Citizen beneficiary;
    private Service issuerService;
    private Town issuerTown;
    private Demand tracking;
    private LocalDate expiration;

    public OfficialDocument() {

    }

    @Builder
    public OfficialDocument(LocalDate creationDate, String documentID, Citizen beneficiary, Service issuerService, Town issuerTown, Demand tracking, LocalDate expiration) {
        this.creationDate = creationDate;
        this.documentID = documentID;
        this.beneficiary = beneficiary;
        this.issuerService = issuerService;
        this.issuerTown = issuerTown;
        this.tracking = tracking;
        this.expiration = expiration;
    }
}

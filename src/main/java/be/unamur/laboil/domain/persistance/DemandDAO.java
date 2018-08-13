package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class DemandDAO {
    private String demandID;
    private LocalDate creationDate;
    private LocalDate sendingDate;
    private String creator;
    private String checker;
    private Boolean signedByCreator;
    private Boolean signedByValidator;
    private Boolean accepted;
    private LocalDate verificationDate;

    @Builder
    public DemandDAO(String demandID, LocalDate creationDate, LocalDate sendingDate, String creator, String checker, Boolean signedByCreator, Boolean signedByValidator, Boolean accepted, LocalDate verificationDate) {
        this.demandID = demandID;
        this.creationDate = creationDate;
        this.sendingDate = sendingDate;
        this.creator = creator;
        this.checker = checker;
        this.signedByCreator = signedByCreator;
        this.signedByValidator = signedByValidator;
        this.accepted = accepted;
        this.verificationDate = verificationDate;
    }
}

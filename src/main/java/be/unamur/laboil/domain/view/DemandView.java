package be.unamur.laboil.domain.view;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class DemandView {
    private String demandID;
    private LocalDate creationDate;
    private LocalDate sendingDate;
    private String creator;
    private String checker;
    private Boolean signedByCreator;
    private Boolean signedByValidator;
    private Boolean accepted;
    private LocalDate verificationDate;

    public DemandView() {

    }

    @Builder
    public DemandView(String demandID, LocalDate creationDate, LocalDate sendingDate, String creator, String checker, Boolean signedByCreator, Boolean signedByValidator, Boolean accepted, LocalDate verificationDate) {
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

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
public class Demand {

    private String demandID;

    private LocalDate creationDate;
    private LocalDate sendingDate;
    private LocalDate verificationDate;

    private Citizen creator;
    private Employee checker;

    private Boolean signedByCreator;
    private Boolean signedByValidator;
    private Boolean accepted;

    public Demand() {
    }

    @Builder
    public Demand(String demandID, LocalDate creationDate, LocalDate sendingDate, LocalDate verificationDate, Citizen creator, Employee checker, Boolean signedByCreator, Boolean signedByValidator, Boolean accepted) {
        this.demandID = demandID;
        this.creationDate = creationDate;
        this.sendingDate = sendingDate;
        this.verificationDate = verificationDate;
        this.creator = creator;
        this.checker = checker;
        this.signedByCreator = signedByCreator;
        this.signedByValidator = signedByValidator;
        this.accepted = accepted;
    }
}


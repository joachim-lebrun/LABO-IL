package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class EventDAO {

    private String eventID;
    private String demandID;
    private String status;
    private String comment;
    private String userID;
    private LocalDate creationDate;


    @Builder

    public EventDAO(String eventID, String demandID, String status, String comment, String userID, LocalDate creationDate) {
        this.eventID = eventID;
        this.demandID = demandID;
        this.status = status;
        this.comment = comment;
        this.userID = userID;
        this.creationDate = creationDate;
    }
}

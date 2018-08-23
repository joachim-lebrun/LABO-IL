package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDAO {

    private String eventID;
    private String demandID;
    private String status;
    private String comment;
    private String userID;
    private LocalDateTime creationTime;
    private int readByEmployee;
    private int readByCitizen;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eventID", eventID)
                .append("demandID", demandID)
                .append("status", status)
                .append("comment", comment)
                .append("userID", userID)
                .append("creationTime", creationTime)
                .toString();
    }
}

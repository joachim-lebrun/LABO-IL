package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Getter
@Setter
@EqualsAndHashCode
public class Event implements Comparable<Event> {


    private String eventID;
    private Demand demand;
    private String comment;
    private User user;
    private EventStatus status;
    private LocalDateTime creationTime;
    private boolean readByCitizen;
    private boolean readByEmployee;

    public Event() {
    }
    @Builder
    public Event(String eventID, Demand demand, String comment, User user, EventStatus status, LocalDateTime creationTime, boolean readByCitizen, boolean readByEmployee) {
        this.eventID = eventID;
        this.demand = demand;
        this.comment = comment;
        this.user = user;
        this.status = status;
        this.creationTime = creationTime;
        this.readByCitizen = readByCitizen;
        this.readByEmployee = readByEmployee;
    }




    @Override
    public int compareTo(Event o) {
        return creationTime.compareTo(o.creationTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eventID", eventID)
                .append("comment", comment)
                .append("status", status)
                .append("creationTime", creationTime)
                .toString();
    }
}

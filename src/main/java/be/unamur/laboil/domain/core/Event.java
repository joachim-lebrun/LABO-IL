package be.unamur.laboil.domain.core;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Data
public class Event implements Comparable<Event> {


    private String eventID;
    private Demand demand;
    private String comment;
    private User user;
    private EventStatus status;
    private LocalDate creationDate;

    public Event() {
    }

    @Builder
    public Event(String eventID, Demand demand, String comment, User user, EventStatus status, LocalDate creationDate) {
        this.eventID = eventID;
        this.demand = demand;
        this.comment = comment;
        this.user = user;
        this.status = status;
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(Event o) {
        return creationDate.compareTo(o.creationDate);
    }
}

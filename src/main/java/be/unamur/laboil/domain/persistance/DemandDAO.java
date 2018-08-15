package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
public class DemandDAO {
    private String demandID;
    private String serviceID;
    private String name;
    private String creator;
    private String verificator;
    private List<EventDAO> history;
    private List<LinkedDocumentDAO> documents;
    private List<DemandInfoDAO> information;

    @Builder

    public DemandDAO(String demandID, String serviceID, String name, String creator, String verificator, List<EventDAO> history, List<LinkedDocumentDAO> documents, List<DemandInfoDAO> information) {
        this.demandID = demandID;
        this.serviceID = serviceID;
        this.name = name;
        this.creator = creator;
        this.verificator = verificator;
        this.history = history;
        this.documents = documents;
        this.information = information;
    }
}

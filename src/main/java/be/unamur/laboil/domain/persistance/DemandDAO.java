package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandDAO {
    private String demandID;
    private String serviceID;
    private String name;
    private String communalName;
    private String creator;
    private String verificator;
    private List<EventDAO> history;
    private List<LinkedDocumentDAO> documents;
    private List<DemandInfoDAO> information;

}

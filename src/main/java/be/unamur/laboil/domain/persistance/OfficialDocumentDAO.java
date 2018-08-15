package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficialDocumentDAO {
    private LocalDate creationDate;
    private String documentID;
    private String demandID;
    private String path;

}

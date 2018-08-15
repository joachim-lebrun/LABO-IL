package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Data
public class DemandInfoDAO {
    private String DemandInfoID;
    private String DemandID;
    private String key;
    private String value;

    @Builder
    public DemandInfoDAO(String demandInfoID, String demandID, String key, String value) {
        DemandInfoID = demandInfoID;
        DemandID = demandID;
        this.key = key;
        this.value = value;
    }
}

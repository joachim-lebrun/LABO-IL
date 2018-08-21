package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Data
public class DemandInfoDAO {
    private String demandInfoID;
    private String demandID;
    private String key;
    private String value;

    @Builder
    public DemandInfoDAO(String demandInfoID, String demandID, String key, String value) {
        this.demandInfoID = demandInfoID;
        this.demandID = demandID;
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("demandInfoID", demandInfoID)
                .append("demandID", demandID)
                .append("key", key)
                .append("value", value)
                .toString();
    }
}

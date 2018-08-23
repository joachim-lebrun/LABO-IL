package be.unamur.laboil.domain.view;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
@Builder
public class SimpleDemandView {

    private String demandID;
    private String serviceName;
    private String name;
    private String address;
    private String startDate;
    private String endDate;
    private String placeSize;
    private String creatorName;
    private String verificatorName;
    private String currentStatus;
    private String createdDate;
}

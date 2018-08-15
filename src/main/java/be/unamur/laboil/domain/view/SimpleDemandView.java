package be.unamur.laboil.domain.view;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class SimpleDemandView {

    private String demandID;
    private String serviceName;
    private String name;
    private String creatorName;
    private String verificatorName;
    private String currentStatus;
    private String createdDate;


    public SimpleDemandView() {

    }

    @Builder

    public SimpleDemandView(String demandID, String serviceName, String name, String creatorName, String verificatorName, String currentStatus, String createdDate) {
        this.demandID = demandID;
        this.serviceName = serviceName;
        this.name = name;
        this.creatorName = creatorName;
        this.verificatorName = verificatorName;
        this.currentStatus = currentStatus;
        this.createdDate = createdDate;
    }
}

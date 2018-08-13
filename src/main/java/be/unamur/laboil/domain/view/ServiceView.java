package be.unamur.laboil.domain.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
public class ServiceView {

    private String serviceName;
    private String serviceTown;

    public ServiceView(){

    }
    @Builder
    public ServiceView(String serviceName, String serviceTown) {
        this.serviceName = serviceName;
        this.serviceTown = serviceTown;
    }
}

package be.unamur.laboil.manager;

import be.unamur.laboil.domain.view.TownView;
import be.unamur.laboil.service.ServiceService;
import be.unamur.laboil.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Component
public class StructureManager {
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private TownService townService;

    public List<TownView> getAllTowns() {
        return townService
                .getAllTowns()
                .stream()
                .map(town -> TownView.builder()
                        .country(town.getCountry())
                        .postalCode(town.getPostalCode())
                        .language(town.getLanguage())
                        .name(town.getName())
                        .build())
                .collect(Collectors.toList());
    }

    /*public List<ServiceView> getAllServices() {
        return serviceService
                .getAllServices()
                .stream()
                .map(service -> ServiceView.builder()
                        .serviceName(service.getServiceName())
                        .serviceTown(service.getServiceTown())
                        .build())
                .collect(Collectors.toList());
    }

    public void add(ServiceView serviceView) {
        Service newService = Service.builder()
                .serviceName(serviceView.getServiceName())
                .serviceTown(serviceView.getServiceTown())
                .build();

        serviceService.insert(newService);
    }



    public void add(TownView townView) {
        Town newTown = Town.builder()
                .townLanguage(townView.getTownLanguage())
                .townName(townView.getTownName())
                .country(townView.getCountry())
                .postalCode(townView.getPostalCode())
                .build();
        townService.insert(newTown);
    }*/
}

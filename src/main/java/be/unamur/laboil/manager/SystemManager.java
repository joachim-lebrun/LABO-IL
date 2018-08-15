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
public class SystemManager {
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
}

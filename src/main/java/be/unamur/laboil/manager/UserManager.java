package be.unamur.laboil.manager;

import be.unamur.laboil.domain.core.Citizen;
import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.core.User;
import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.domain.view.EmployeeView;
import be.unamur.laboil.domain.view.SimpleDemandView;
import be.unamur.laboil.domain.view.form.DemandForm;
import be.unamur.laboil.service.CitizenService;
import be.unamur.laboil.service.DemandService;
import be.unamur.laboil.service.EmployeeService;
import be.unamur.laboil.service.OfficialDocumentService;
import be.unamur.laboil.service.ServiceService;
import be.unamur.laboil.service.TownService;
import be.unamur.laboil.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Component
public class UserManager {

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private OfficialDocumentService officialDocumentService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private TownService townService;


    public CitizenView getCitizen(String userId) {
        Citizen citizen = citizenService.findById(userId);
        return CitizenView.builder()
                .userID(citizen.getUserID())
                .firstName(citizen.getFirstName())
                .lastName(citizen.getLastName()).build();
    }
    public CitizenView getCitizenWithEmail(String email) {
        Citizen citizen = citizenService.findByEmail(email);
        return CitizenView.builder()
                .userID(citizen.getUserID())
                .firstName(citizen.getFirstName())
                .lastName(citizen.getLastName()).build();
    }

    public List<CitizenView> getAllCitizens() {
        return citizenService
                .getAllCitizens()
                .stream()
                .map(citizen -> CitizenView.builder()
                        .address(citizen.getAddress())
                        .birthDate(citizen.getBirthDate().format(Constants.FORMATTER))
                        .email(citizen.getEmail())
                        .firstName(citizen.getFirstName())
                        .lastName(citizen.getLastName())
                        .phoneNumber(citizen.getPhoneNumber())
                        .userID(citizen.getUserID()).build())
                .collect(Collectors.toList());
    }

    public void add(CitizenView citizenView) {
        Citizen newCitizen = Citizen.builder()
                .address(citizenView.getAddress())
                .birthDate(LocalDate.parse(citizenView.getBirthDate(), Constants.FORMATTER))
                .email(citizenView.getEmail())
                .firstName(citizenView.getFirstName())
                .lastName(citizenView.getLastName())
                .phoneNumber(citizenView.getPhoneNumber())
                .userID(citizenView.getUserID())
                .password(citizenView.getPassword())
                .natReg(citizenView.getNatReg())
                .town(Town.builder()
                        .name(citizenView.getTown())
                        .build())
                .build();

        citizenService.insert(newCitizen);
    }

    public List<EmployeeView> getAllEmployees() {
        return employeeService
                .getAllEmployees()
                .stream()
                .map(employee -> EmployeeView.builder()
                        .address(employee.getAddress())
                        .birthDate(employee.getBirthDate().format(Constants.FORMATTER))
                        .email(employee.getEmail())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .phoneNumber(employee.getPhoneNumber())
                        .service(employee.getService().getName())
                        .userID(employee.getUserID()).build())
                .collect(Collectors.toList());
    }

    public boolean validate(String email, String userID) {
        User user = citizenService.findByEmail(email);
        boolean isValid = userID.equalsIgnoreCase(user.getUserID());
        if (!isValid) {
            user = employeeService.findByEmail(email);
            isValid = userID.equalsIgnoreCase(user.getUserID());
        }
        return isValid;
    }

    public List<SimpleDemandView> getMyDemands(String userID) {
        return demandService
                .getMyDemands(userID)
                .stream()
                .map(demand -> SimpleDemandView.builder()
                        .creatorName(String.format("%s %s", demand.getCreator().getLastName(), demand.getCreator().getFirstName()))
                        .currentStatus(demand.getHistory().last().getStatus().name())
                        .createdDate(demand.getHistory().first().getCreationDate().format(Constants.FORMATTER))
                        .demandID(demand.getDemandID())
                        .name(demand.getName())
                        .serviceName(demand.getService().getName())
                        .verificatorName(String.format("%s %s", demand.getVerificator().getLastName(), demand.getVerificator().getFirstName()))
                        .build())
                .collect(Collectors.toList());
    }

    public void createDemand(DemandForm demandForm) {
        Demand newDemand = Demand.builder()
                .service(serviceService.findById(demandForm.getServiceId()))
                .name(demandForm.getName())
                .documents(demandForm.getLinkedDocuments())
                .creator(citizenService.findById(demandForm.getUserID()))
                .town(townService.findById(demandForm.getTownID()))
                .build();
        demandService.insert(newDemand, demandForm.getComment());
    }
}

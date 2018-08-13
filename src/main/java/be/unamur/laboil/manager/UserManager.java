package be.unamur.laboil.manager;

import be.unamur.laboil.domain.core.Citizen;
import be.unamur.laboil.domain.core.Company;
import be.unamur.laboil.domain.core.Employee;
import be.unamur.laboil.domain.core.Gender;
import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.domain.view.CompanyView;
import be.unamur.laboil.domain.view.EmployeeView;
import be.unamur.laboil.domain.view.form.EmployeeForm;
import be.unamur.laboil.service.CitizenService;
import be.unamur.laboil.service.CompanyService;
import be.unamur.laboil.service.EmployeeService;
import be.unamur.laboil.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceService serviceService;


    public CitizenView getCitizen(String userId) {
        Citizen citizen = citizenService.findById(userId);
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
                        .birthDate(citizen.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .birthPlace(citizen.getBirthPlace())
                        .email(citizen.getEmail())
                        .firstName(citizen.getFirstName())
                        .gender(String.valueOf(citizen.getGender()))
                        .lastName(citizen.getLastName())
                        .phoneNumber(citizen.getPhoneNumber())
                        .userID(citizen.getUserID()).build())
                .collect(Collectors.toList());
    }

    public void add(CitizenView citizenView) {
        Citizen newCitizen = Citizen.builder()
                .address(citizenView.getAddress())
                .birthDate(LocalDate.parse(citizenView.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .birthPlace(citizenView.getBirthPlace())
                .email(citizenView.getEmail())
                .firstName(citizenView.getFirstName())
                .gender(Gender.fromAbbr(citizenView.getGender()))
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

    public List<CompanyView> getAllCompanies() {
        return companyService
                .getAllCompanies()
                .stream()
                .map(company -> CompanyView.builder()
                        .companyAddress(company.getAddress())
                        .companyName(company.getName())
                        .companyPhone(company.getPhone())
                        .companyVATNumber(company.getVatNumber())
                        .build())
                .collect(Collectors.toList());

    }

    public void add(CompanyView companyView) {
        Company newCompany = Company.builder()
                .address(companyView.getCompanyAddress())
                .name(companyView.getCompanyName())
                .phone(companyView.getCompanyPhone())
                .vatNumber(companyView.getCompanyVATNumber())
                .build();
        companyService.insert(newCompany);
    }

    public List<EmployeeView> getAllEmployees() {
        return employeeService
                .getAllEmployees()
                .stream()
                .map(employee -> EmployeeView.builder()
                        .address(employee.getAddress())
                        .birthDate(employee.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .email(employee.getEmail())
                        .firstName(employee.getFirstName())
                        .gender(String.valueOf(employee.getGender()))
                        .lastName(employee.getLastName())
                        .phoneNumber(employee.getPhoneNumber())
                        .role(employee.getRole())
                        .service(employee.getService().getName())
                        .userID(employee.getUserID()).build())
                .collect(Collectors.toList());
    }

    public void add(EmployeeForm employeeView) {
        Employee newEmployee = Employee.builder()
                .address(employeeView.getAddress())
                .birthDate(LocalDate.parse(employeeView.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .email(employeeView.getEmail())
                .firstName(employeeView.getFirstName())
                .gender(Gender.fromAbbr(employeeView.getGender()))
                .lastName(employeeView.getLastName())
                .phoneNumber(employeeView.getPhoneNumber())
                .role(employeeView.getRole())
                .service(serviceService.findById(employeeView.getService()))
                .userID(employeeView.getUserID())
                .build();
        employeeService.insert(newEmployee);

    }


}

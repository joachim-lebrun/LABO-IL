package be.unamur.laboil.service;

import be.unamur.laboil.component.Encryptor;
import be.unamur.laboil.domain.core.Employee;
import be.unamur.laboil.domain.core.Gender;
import be.unamur.laboil.domain.persistance.EmployeeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private Encryptor encryptor;


    public Employee findById(String userId) {
        EmployeeDAO dao = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE where USER_ID = ?",
                new Object[]{userId}, (resultSet, i) -> EmployeeDAO.builder()
                        .firstName(resultSet.getString("FIRST_NAME"))
                        .lastName(resultSet.getString("LAST_NAME"))
                        .address(resultSet.getString("ADDRESS"))
                        .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                        .birthDate(resultSet.getString("BIRTH_DATE"))
                        .email(resultSet.getString("EMAIL"))
                        .gender(resultSet.getString("GENDER"))
                        .service(resultSet.getString("SERVICE"))
                        .userID(resultSet.getString("USER_ID"))
                        .role(resultSet.getString("ROLE"))
                        .natReg(resultSet.getString("REG_NAT"))
                        .build());


        return Employee.builder()
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .address(dao.getAddress())
                .phoneNumber(dao.getPhoneNumber())
                .birthDate(LocalDate.parse(encryptor.decrypt(dao.getBirthDate())))
                .email(dao.getEmail())
                .gender(Gender.fromAbbr(dao.getGender()))
                .service(serviceService.findById(dao.getService()))
                .role(dao.getRole())
                .natReg(encryptor.decrypt(dao.getNatReg()))
                .userID(dao.getUserID()).build();
    }


    public List<Employee> getAllEmployees() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE");
        return rows
                .stream()
                .map(row -> Employee.builder()
                        .firstName((String) row.get("FIRST_NAME"))
                        .lastName((String) row.get("LAST_NAME"))
                        .address((String) row.get("ADDRESS"))
                        .phoneNumber((String) row.get("PHONE_NUMBER"))
                        .birthDate(LocalDate.parse((String) row.get("BIRTH_DATE")))
                        .email((String) row.get("EMAIL"))
                        .gender(Gender.fromAbbr((String) row.get("GENDER")))
                        .service(serviceService.findById((String) row.get("SERVICE")))
                        .role((String) row.get("ROLE"))
                        .userID((String) row.get("USER_ID"))
                        .natReg((String) row.get("REG_NAT"))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(Employee employee) {
        EmployeeDAO dao = EmployeeDAO.builder()
                .address(employee.getAddress())
                .birthDate(encryptor.encrypt(employee.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE)))
                .service(employee.getService().getServiceID())
                .role(employee.getRole())
                .email(employee.getEmail())
                .firstName(employee.getFirstName())
                .gender(employee.getGender().getAbbr())
                .lastName(employee.getLastName())
                .userID(UUID.randomUUID().toString())
                .phoneNumber(employee.getPhoneNumber()).build();
        jdbcTemplate.update("INSERT INTO EMPLOYEE (FIRST_NAME,LAST_NAME,ADDRESS,BIRTH_DATE,SERVICE,ROLE,EMAIL,GENDER,USER_ID,PASSWORD,REG_NAT,ENABLED) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                dao.getFirstName(),
                dao.getLastName(),
                dao.getAddress(),
                dao.getBirthDate(),
                dao.getService(),
                dao.getRole(),
                dao.getEmail(),
                dao.getGender(),
                dao.getUserID(),
                dao.getPassword(),
                dao.getNatReg(),
                true);
        LOGGER.debug("employee {} has been added", dao.toString());
    }

}

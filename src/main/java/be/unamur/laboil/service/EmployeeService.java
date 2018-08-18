package be.unamur.laboil.service;

import be.unamur.laboil.component.Encryptor;
import be.unamur.laboil.domain.core.Employee;
import be.unamur.laboil.domain.persistance.EmployeeDAO;
import be.unamur.laboil.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private Encryptor encryptor;


    public Employee findById(String userId) {
        EmployeeDAO dao = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE e JOIN USER u on u.USER_ID=e.USER_ID where u.USER_ID = ?",
                new Object[]{userId}, (resultSet, i) -> extractFromRS(resultSet));
        return buildFromDAO(dao);
    }

    private Employee buildFromDAO(EmployeeDAO dao) {
        Employee employee = Employee.builder()
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .address(dao.getAddress())
                .phoneNumber(dao.getPhoneNumber())
                .birthDate(LocalDate.parse(encryptor.decrypt(dao.getBirthDate()), Constants.SQL_FORMATTER))
                .email(dao.getEmail())
                .service(serviceService.findByIdFromEmployeeSearch(dao.getService(), dao.getUserID()))
                .natReg(encryptor.decrypt(dao.getNatReg()))
                .userID(dao.getUserID())
                .empoyeeID(dao.getEmployeeID())
                .build();
        if (employee.getService().getAdministrator() == null) {
            employee.getService().setAdministrator(employee);
        }
        return employee;
    }

    private Employee buildMayorFromDAO(EmployeeDAO dao) {
        Employee employee = Employee.builder()
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .address(dao.getAddress())
                .phoneNumber(dao.getPhoneNumber())
                .birthDate(LocalDate.parse(encryptor.decrypt(dao.getBirthDate()), Constants.SQL_FORMATTER))
                .email(dao.getEmail())
                .natReg(encryptor.decrypt(dao.getNatReg()))
                .userID(dao.getUserID())
                .empoyeeID(dao.getEmployeeID())
                .build();
        return employee;
    }

    public Employee findByEmail(String email) {
        EmployeeDAO dao = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE e JOIN USER u on u.USER_ID=e.USER_ID where EMAIL = ?",
                new Object[]{email}, (resultSet, i) -> extractFromRS(resultSet));
        return buildFromDAO(dao);
    }

    private EmployeeDAO extractFromRS(ResultSet resultSet) throws SQLException {
        return EmployeeDAO.builder()
                .firstName(resultSet.getString("FIRST_NAME"))
                .lastName(resultSet.getString("LAST_NAME"))
                .address(resultSet.getString("ADDRESS"))
                .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                .birthDate(resultSet.getString("BIRTH_DATE"))
                .email(resultSet.getString("EMAIL"))
                .service(resultSet.getString("SERVICE_ID"))
                .userID(resultSet.getString("USER_ID"))
                .natReg(resultSet.getString("REG_NAT"))
                .employeeID(resultSet.getString("EMPLOYEE_ID"))
                .build();
    }


    public List<Employee> getAllEmployees() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE e JOIN USER u on u.USER_ID=e.USER_ID");
        return rows
                .stream()
                .map(this::EmployeeFromRow)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesOfService(String serviceID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EMPLOYEE e JOIN USER u on u.USER_ID=e.USER_ID where SERVICE_ID = ?", serviceID);
        return rows
                .stream()
                .map(this::EmployeeFromRow)
                .collect(Collectors.toList());
    }

    private Employee EmployeeFromRow(Map<String, Object> row) {
        return Employee.builder()
                .firstName((String) row.get("FIRST_NAME"))
                .lastName((String) row.get("LAST_NAME"))
                .address((String) row.get("ADDRESS"))
                .phoneNumber((String) row.get("PHONE_NUMBER"))
                .birthDate(LocalDate.parse((String) row.get("BIRTH_DATE"), Constants.SQL_FORMATTER))
                .email((String) row.get("EMAIL"))
                .service(serviceService.findById((String) row.get("SERVICE")))
                .userID((String) row.get("USER_ID"))
                .natReg((String) row.get("REG_NAT"))
                .empoyeeID((String) row.get("EMPLOYEE_ID"))
                .build();
    }

    public Employee findMayor(String mayorID) {
        EmployeeDAO dao = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE e JOIN USER u on u.USER_ID=e.USER_ID where u.USER_ID = ?",
                new Object[]{mayorID}, (resultSet, i) -> extractFromRS(resultSet));
        return buildMayorFromDAO(dao);
    }
}

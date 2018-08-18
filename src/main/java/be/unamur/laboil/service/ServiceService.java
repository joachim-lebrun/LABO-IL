package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Service;
import be.unamur.laboil.domain.persistance.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TownService townService;
    @Autowired
    private EmployeeService employeeService;

    public Service findById(String serviceID) {
        ServiceDAO dao = jdbcTemplate.queryForObject("SELECT * FROM SERVICE where SERVICE_ID = ?",
                new Object[]{serviceID}, (resultSet, i) -> extractResultSet(resultSet));

        return buildFromDAO(dao, "");
    }

    public Service findByIdFromEmployeeSearch(String serviceID, String employeeId) {
        ServiceDAO dao = jdbcTemplate.queryForObject("SELECT * FROM SERVICE where SERVICE_ID = ?",
                new Object[]{serviceID}, (resultSet, i) -> extractResultSet(resultSet));

        return buildFromDAO(dao, employeeId);
    }

    private Service buildFromDAO(ServiceDAO dao, String employeeId) {
        return Service.builder()
                .name(dao.getName())
                .town(townService.findById(dao.getTown()))
                .administrator(!employeeId.equalsIgnoreCase(dao.getAdministrator()) ? employeeService.findById(dao.getAdministrator()) : null)
                .serviceID(dao.getServiceID())
                .address(dao.getAddress())
                .build();
    }

    private ServiceDAO extractResultSet(ResultSet resultSet) throws SQLException {
        return ServiceDAO.builder()
                .name(resultSet.getString("NAME"))
                .town(resultSet.getString("TOWN"))
                .address(resultSet.getString("ADDRESS"))
                .administrator(resultSet.getString("ADMINISTRATOR"))
                .serviceID(resultSet.getString("SERVICE_ID"))
                .build();
    }
}

package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Service;
import be.unamur.laboil.domain.persistance.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .town(resultSet.getString("TOWN_ID"))
                .address(resultSet.getString("ADDRESS"))
                .administrator(resultSet.getString("ADMINISTRATOR"))
                .serviceID(resultSet.getString("SERVICE_ID"))
                .build();
    }

    public List<File> findDocumentsOf(String demandID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM LINK_DOCUMENT where DEMAND_ID = ?", demandID);
        return rows
                .stream()
                .map(row -> Paths.get((String) row.get("PATH")).toFile())
                .collect(Collectors.toList());
    }

    public List<Service> findAllOfTown(String townID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM SERVICE where TOWN_ID = ?", townID);
        return rows
                .stream()
                .map(row -> ServiceDAO.builder()
                        .name((String) row.get("NAME"))
                        .town((String) row.get("TOWN_ID"))
                        .address((String) row.get("ADDRESS"))
                        .administrator((String) row.get("ADMINISTRATOR"))
                        .serviceID((String) row.get("SERVICE_ID"))
                        .build())
                .map(dao -> buildFromDAO(dao, ""))
                .collect(Collectors.toList());
    }
}

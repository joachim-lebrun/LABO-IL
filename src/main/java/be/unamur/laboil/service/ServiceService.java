package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Service;
import be.unamur.laboil.domain.persistance.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TownService townService;

    public Service findById(String serviceID) {
        ServiceDAO dao = jdbcTemplate.queryForObject("SELECT * FROM SERVICE where SERVICE_ID = ?",
                new Object[]{serviceID}, (resultSet, i) -> ServiceDAO.builder()
                        .name(resultSet.getString("SERVICE_NAME"))
                        .town(resultSet.getString("SERVICE_TOWN"))
                        .serviceID(resultSet.getString("SERVICE_ID"))
                        .build());

        return Service.builder()
                .name(dao.getName())
                .town(townService.findById(dao.getTown()))
                .serviceID(dao.getServiceID())
                .build();
    }
}

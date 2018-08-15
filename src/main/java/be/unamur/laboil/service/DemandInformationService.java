package be.unamur.laboil.service;

import be.unamur.laboil.domain.persistance.DemandInfoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Service
public class DemandInformationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandInformationService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Map<String, String> findInformationsOf(String demandID) {
        final Map<String, String> infoMap = new HashMap<>();
        jdbcTemplate.queryForList("SELECT * FROM DEMAND_INFO where DEMAND_ID = ?", demandID)
                .forEach(row -> infoMap.put((String) row.get("INFO_KEY"), (String) row.get("INFO_VALUE")));
        return infoMap;

    }

    public void insert(String demandID, String key, String value) {
        DemandInfoDAO dao = DemandInfoDAO.builder()
                .demandID(demandID)
                .key(key)
                .demandInfoID(UUID.randomUUID().toString())
                .value(value)
                .build();
        jdbcTemplate.update("INSERT INTO DEMAND_INFO (DEMAND_INFO_ID,DEMAND_ID,INFO_KEY,INFO_VALUE) VALUES (?,?,?,?)",
                dao.getDemandInfoID(),
                dao.getDemandID(),
                dao.getKey(),
                dao.getValue());
        LOGGER.debug("DemandInformation {} has been added", dao.toString());
    }
}

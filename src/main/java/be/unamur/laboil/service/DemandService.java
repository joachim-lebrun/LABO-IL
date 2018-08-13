package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.persistance.DemandDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class DemandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemandService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceService serviceService;


    public Demand findByID(String demandID) {
        DemandDAO dao = jdbcTemplate.queryForObject("SELECT * FROM DEMAND where DEMAND_ID = ?",
                new Object[]{demandID}, (resultSet, i) -> DemandDAO.builder()
                        .accepted(resultSet.getBoolean("ACCEPTED"))
                        .checker(resultSet.getString("VERIFICATOR"))
                        .creationDate(resultSet.getDate("CREATION_DATE").toLocalDate())
                        .creator(resultSet.getString("CREATOR"))
                        .demandID(resultSet.getString("DEMAND_ID"))
                        .sendingDate(resultSet.getDate("SENDING_DATE").toLocalDate())
                        .signedByCreator(resultSet.getBoolean("SIGNED_BY_CREATOR"))
                        .signedByValidator(resultSet.getBoolean("SIGNED_BY_VERIFICATOR"))
                        .verificationDate(resultSet.getDate("VERIFICATION_DATE").toLocalDate())
                        .build());

        return Demand.builder()
                .accepted(dao.getAccepted())
                .checker(employeeService.findById(dao.getChecker()))
                .creationDate(dao.getCreationDate())
                .creator(citizenService.findById(dao.getCreator()))
                .demandID(dao.getDemandID())
                .sendingDate(dao.getSendingDate())
                .signedByCreator(dao.getSignedByCreator())
                .signedByValidator(dao.getSignedByValidator())
                .verificationDate(dao.getVerificationDate())
                .build();


    }

    public List<Demand> getAllDemands() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DEMAND");
        return rows
                .stream()
                .map(row -> Demand.builder()
                        .accepted((Boolean) row.get("ACCEPTED"))
                        .checker(employeeService.findById((String) row.get("VERIFICATOR")))
                        .creationDate(LocalDate.parse((String) row.get("CREATION_DATE")))
                        .creator(citizenService.findById((String) row.get("CREATOR")))
                        .demandID((String) row.get("DEMAND_ID"))
                        .sendingDate(LocalDate.parse((String) row.get("SENDING_DATE")))
                        .signedByCreator((Boolean) row.get("SIGNED_BY_CREATOR"))
                        .signedByValidator((Boolean) row.get("SIGNED_BY_VERIFICATOR"))
                        .verificationDate(LocalDate.parse((String) row.get("VERIFICATION_DATE")))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(Demand demand) {
        DemandDAO dao = DemandDAO.builder()
                .verificationDate(demand.getVerificationDate())
                .signedByValidator(demand.getSignedByValidator())
                .signedByCreator(demand.getSignedByCreator())
                .sendingDate(demand.getSendingDate())
                .demandID(UUID.randomUUID().toString())
                .creator(demand.getCreator().getUserID())
                .creationDate(demand.getCreationDate())
                .checker(demand.getChecker().getUserID())
                .accepted(demand.getAccepted())
                .build();

        jdbcTemplate.update("INSERT INTO DEMAND (DEMAND_ID, CREATION_DATE, SENDING_DATE , CREATOR, VERIFICATOR , SIGNED_BY_CREATOR, SIGNED_BY_VERIFICATOR, ACCEPTED, VERIFICATION_DATE) VALUES (?,?,?,?,?,?,?,?,?)",
                dao.getDemandID(),
                dao.getCreationDate(),
                dao.getSendingDate(),
                dao.getCreator(),
                dao.getChecker(),
                dao.getSignedByCreator(),
                dao.getSignedByValidator(),
                dao.getAccepted(),
                dao.getVerificationDate());
        LOGGER.debug("demand {} has been added", dao.toString());
    }

}

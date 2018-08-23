package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.core.Event;
import be.unamur.laboil.domain.core.EventStatus;
import be.unamur.laboil.domain.persistance.DemandDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
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
    @Autowired
    private EventService eventService;
    @Autowired
    private LinkedDocumentService linkedDocumentService;
    @Autowired
    private DemandInformationService demandInformationService;


    public Demand findByID(String demandID) {
        TreeSet<Event> history = eventService.findHistoryOf(demandID);
        DemandDAO dao = jdbcTemplate.queryForObject("SELECT * FROM DEMAND where DEMAND_ID = ?",
                new Object[]{demandID}, (resultSet, i) -> extractFromRS(resultSet));

        Demand demand = Demand.builder()
                .verificator(dao.getVerificator() == null ? null : employeeService.findById(dao.getVerificator()))
                .creator(citizenService.findById(dao.getCreator()))
                .demandID(dao.getDemandID())
                .name(dao.getName())
                .communalName(dao.getCommunalName())
                .service(serviceService.findById(dao.getServiceID()))
                .documents(linkedDocumentService.findDocumentsOf(demandID))
                .information(demandInformationService.findInformationsOf(demandID))
                .build();
        history.forEach(item -> item.setDemand(demand));
        demand.setHistory(history);
        return demand;


    }

    private DemandDAO extractFromRS(ResultSet resultSet) throws SQLException {
        return DemandDAO.builder()
                .verificator(resultSet.getString("VERIFICATOR"))
                .creator(resultSet.getString("CREATOR"))
                .demandID(resultSet.getString("DEMAND_ID"))
                .name(resultSet.getString("NAME"))
                .serviceID(resultSet.getString("SERVICE_ID"))
                .communalName(resultSet.getString("COMMUNAL_NAME"))
                .build();
    }

    public List<Demand> getMyDemands(String userID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DEMAND where CREATOR = ?", userID);
        return extractFromRows(rows);
    }

    public void insert(Demand demand, String comment) {
        String demandID = UUID.randomUUID().toString();
        demand.setDemandID(demandID);
        eventService.insert(Event.builder()
                .status(EventStatus.NEW)
                .user(demand.getCreator())
                .demand(demand)
                .comment(comment)
                .build());

        DemandDAO dao = DemandDAO.builder()
                .demandID(demandID)
                .serviceID(demand.getService().getServiceID())
                .name(demand.getName())
                .creator(demand.getCreator().getUserID())
                .verificator(demand.getVerificator() == null ? null : demand.getVerificator().getUserID())
                .build();
        demand.getDocuments().forEach(file -> linkedDocumentService.insert(demandID, file));
        demand.getInformation().forEach((key, value) -> demandInformationService.insert(demandID, key, value));
        jdbcTemplate.update("INSERT INTO DEMAND (DEMAND_ID, NAME , SERVICE_ID , CREATOR, VERIFICATOR) VALUES (?,?,?,?,?)",
                dao.getDemandID(),
                dao.getName(),
                dao.getServiceID(),
                dao.getCreator(),
                dao.getVerificator());
        LOGGER.debug("demand {} has been added", dao.toString());
    }

    public List<Demand> findPendingDemandByService(String serviceID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT *" +
                        " FROM DEMAND d" +
                        " where SERVICE_ID = ?" +
                        "  AND NOT EXISTS(SELECT * FROM EVENT e WHERE e.DEMAND_ID = d.DEMAND_ID " +
                        "                                         AND (e.STATUS like 'ACCEPTED' OR e.STATUS like 'REFUSED'))",
                serviceID);

        return extractFromRows(rows);
    }

    private List<Demand> extractFromRows(List<Map<String, Object>> rows) {
        return rows
                .stream()
                .map(row -> {
                    String demandID = (String) row.get("DEMAND_ID");
                    TreeSet<Event> history = eventService.findHistoryOf(demandID);
                    Demand demand = Demand.builder()
                            .verificator(row.get("VERIFICATOR") == null ? null : employeeService.findById((String) row.get("VERIFICATOR")))
                            .creator(citizenService.findById((String) row.get("CREATOR")))
                            .name((String) row.get("NAME"))
                            .service(serviceService.findById((String) row.get("SERVICE_ID")))
                            .documents(linkedDocumentService.findDocumentsOf(demandID))
                            .information(demandInformationService.findInformationsOf(demandID))
                            .demandID(demandID)
                            .communalName((String) row.get("COMMUNAL_NAME"))
                            .build();
                    history.forEach(item -> item.setDemand(demand));
                    demand.setHistory(history);
                    return demand;
                })
                .collect(Collectors.toList());
    }

    public void update(Demand demand) {
        DemandDAO dao = DemandDAO.builder()
                .demandID(demand.getDemandID())
                .communalName(demand.getCommunalName())
                .serviceID(demand.getService().getServiceID())
                .name(demand.getName())
                .creator(demand.getCreator().getUserID())
                .verificator(demand.getVerificator() == null ? null : demand.getVerificator().getUserID())
                .build();
        jdbcTemplate.update("UPDATE DEMAND" +
                " SET VERIFICATOR = ?, COMMUNAL_NAME = ?" +
                " WHERE DEMAND_ID = ?", dao.getVerificator(), dao.getCommunalName(), dao.getDemandID());
    }
}

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
                new Object[]{demandID}, (resultSet, i) -> DemandDAO.builder()
                        .verificator(resultSet.getString("VERIFICATOR"))
                        .creator(resultSet.getString("CREATOR"))
                        .demandID(resultSet.getString("DEMAND_ID"))
                        .name(resultSet.getString("NAME"))
                        .serviceID(resultSet.getString("SERVICE_ID"))
                        .build());

        Demand demand = Demand.builder()
                .verificator(employeeService.findById(dao.getVerificator()))
                .creator(citizenService.findById(dao.getCreator()))
                .demandID(dao.getDemandID())
                .name(dao.getName())
                .service(serviceService.findById(dao.getServiceID()))
                .documents(linkedDocumentService.findDocumentsOf(demandID))
                .information(demandInformationService.findInformationsOf(demandID))
                .build();
        history.forEach(item -> item.setDemand(demand));
        demand.setHistory(history);
        return demand;


    }

    public List<Demand> getMyDemands(String userID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM DEMAND where CREATOR = ?", userID);
        return rows
                .stream()
                .map(row -> {
                    String demandID = (String) row.get("DEMAND_ID");
                    TreeSet<Event> history = eventService.findHistoryOf(demandID);
                    Demand demand = Demand.builder()
                            .verificator(employeeService.findById((String) row.get("VERIFICATOR")))
                            .creator(citizenService.findById((String) row.get("CREATOR")))
                            .name((String) row.get("NAME"))
                            .service(serviceService.findById((String) row.get("SERVICE_ID")))
                            .documents(linkedDocumentService.findDocumentsOf(demandID))
                            .information(demandInformationService.findInformationsOf(demandID))
                            .demandID(demandID)
                            .build();
                    history.forEach(item -> item.setDemand(demand));
                    demand.setHistory(history);
                    return demand;
                })
                .collect(Collectors.toList());
    }

    public void insert(Demand demand, String comment) {
        eventService.insert(Event.builder()
                .status(EventStatus.NEW)
                .user(demand.getCreator())
                .demand(demand)
                .comment(comment)
                .build());

        String demandID = UUID.randomUUID().toString();
        DemandDAO dao = DemandDAO.builder()
                .demandID(demandID)
                .name(demand.getName())
                .creator(demand.getCreator().getUserID())
                .verificator(demand.getVerificator().getUserID())
                .build();
        demand.getDocuments().forEach(file -> linkedDocumentService.insert(demandID, file));
        demand.getInformation().forEach((key, value) -> demandInformationService.insert(demandID, key, value));
        jdbcTemplate.update("INSERT INTO DEMAND (DEMAND_ID, NAME , CREATION_DATE, SERVICE_ID , CREATOR, VERIFICATOR) VALUES (?,?,?,?,?,?)",
                dao.getDemandID(),
                dao.getName(),
                dao.getServiceID(),
                dao.getCreator(),
                dao.getVerificator());
        LOGGER.debug("demand {} has been added", dao.toString());
    }

}

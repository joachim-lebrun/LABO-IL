package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Event;
import be.unamur.laboil.domain.core.EventStatus;
import be.unamur.laboil.domain.core.User;
import be.unamur.laboil.domain.persistance.EventDAO;
import be.unamur.laboil.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CitizenService citizenService;
    @Autowired
    private EmployeeService employeeService;

    public void insert(Event event) {
        EventDAO dao = EventDAO.builder()
                .status(event.getStatus().name())
                .creationTime(LocalDateTime.now())
                .demandID(event.getDemand().getDemandID())
                .eventID(UUID.randomUUID().toString())
                .comment(event.getComment())
                .userID(event.getUser().getUserID())
                .build();
        jdbcTemplate.update("INSERT INTO EVENT (EVENT_ID,DEMAND_ID,COMMENT,STATUS,USER_ID,CREATION_TIME) VALUES (?,?,?,?,?,?)",
                dao.getEventID(),
                dao.getDemandID(),
                dao.getComment(),
                dao.getStatus(),
                dao.getUserID(),
                dao.getCreationTime().format(Constants.SQL_DT_FORMATTER));
        LOGGER.debug("event {} has been added", dao.toString());
    }

    public TreeSet<Event> findHistoryOf(String demandID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EVENT where DEMAND_ID = ?", demandID);
        return rows
                .stream()
                .map(row -> {
                    Event event = Event.builder()
                            .status(EventStatus.valueOf((String) row.get("STATUS")))
                            .creationTime(LocalDateTime.parse((String) row.get("CREATION_TIME"), Constants.SQL_DT_FORMATTER))
                            .eventID((String) row.get("EVENT_ID"))
                            .comment((String) row.get("COMMENT"))
                            .build();
                    String userId = (String) row.get("USER_ID");
                    User user = citizenService.findById(userId);
                    if (user == null) {
                        user = employeeService.findById(userId);
                    }
                    event.setUser(user);
                    return event;
                })
                .collect(Collectors.toCollection(TreeSet::new));
    }
}

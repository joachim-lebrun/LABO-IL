package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Event;
import be.unamur.laboil.domain.core.EventStatus;
import be.unamur.laboil.domain.persistance.EventDAO;
import be.unamur.laboil.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void insert(Event event) {
        EventDAO dao = EventDAO.builder()
                .status(event.getStatus().name())
                .creationDate(LocalDate.now())
                .demandID(event.getDemand().getDemandID())
                .eventID(UUID.randomUUID().toString())
                .comment(event.getComment())
                .userID(event.getUser().getUserID())
                .build();
        jdbcTemplate.update("INSERT INTO EVENT (EVENT_ID,DEMAND_ID,COMMENT,STATUS,USER_ID,CREATION_DATE) VALUES (?,?,?,?,?,?)",
                dao.getEventID(),
                dao.getDemandID(),
                dao.getComment(),
                dao.getStatus(),
                dao.getUserID(),
                dao.getCreationDate().format(Constants.SQL_FORMATTER));
        LOGGER.debug("event {} has been added", dao.toString());
    }

    public TreeSet<Event> findHistoryOf(String demandID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM EVENT where DEMAND_ID = ?", demandID);
        return rows
                .stream()
                .map(row -> Event.builder()
                        .status(EventStatus.valueOf((String) row.get("STATUS")))
                        .creationDate(LocalDate.parse((String) row.get("CREATION_DATE"), Constants.SQL_FORMATTER))
                        .eventID((String) row.get("EVENT_ID"))
                        .build())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}

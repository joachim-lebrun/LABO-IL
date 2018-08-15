package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.persistance.LinkedDocumentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Service
public class LinkedDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedDocumentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<File> findDocumentsOf(String demandID) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM LINK_DOCUMENT where DEMAND_ID = ?", demandID);
        return rows
                .stream()
                .map(row -> Paths.get((String) row.get("PATH")).toFile())
                .collect(Collectors.toList());
    }

    public void insert(String demandID, File file) {
        LinkedDocumentDAO dao = LinkedDocumentDAO.builder()
                .path(file.getAbsolutePath())
                .demandID(demandID)
                .linkedDocumentID(UUID.randomUUID().toString())
                .name(file.getName())
                .build();
        jdbcTemplate.update("INSERT INTO LINK_DOCUMENT (LINK_DOCUMENT_ID,DEMAND_ID,PATH,NAME) VALUES (?,?,?,?)",
                dao.getLinkedDocumentID(),
                dao.getDemandID(),
                dao.getPath(),
                dao.getName());
        LOGGER.debug("document {} has been added", dao.toString());
    }

}

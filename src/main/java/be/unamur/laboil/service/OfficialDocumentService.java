package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.OfficialDocument;
import be.unamur.laboil.domain.persistance.OfficialDocumentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class OfficialDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficialDocumentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DemandService demandService;


    public OfficialDocument findByID(String docID) {
        OfficialDocumentDAO dao = jdbcTemplate.queryForObject("SELECT * FROM OFFICIAL_DOC where DOCUMENT_ID = ?",
                new Object[]{docID}, (resultSet, i) -> OfficialDocumentDAO.builder()
                        .documentID(resultSet.getString("DOCUMENT_ID"))
                        .demandID(resultSet.getString("DEMAND_ID"))
                        .path(resultSet.getString("PATH"))
                        .creationDate(resultSet.getDate("CREATION_DATE").toLocalDate())
                        .build());

        return OfficialDocument.builder()
                .documentID(dao.getDocumentID())
                .creationDate(dao.getCreationDate())
                .demand(demandService.findByID(dao.getDemandID()))
                .pdf(Paths.get(dao.getPath()).toFile())
                .build();


    }


    public List<OfficialDocument> getMyOfficialDocuments(String userID) {
        return jdbcTemplate.queryForList("SELECT * FROM OFFICIAL_DOC o " +
                "JOIN DEMAND d on d.DEMAND_ID = o.DEMAND_ID " +
                "where d.CREATOR = ?", userID).stream()
                .map(row -> OfficialDocument.builder()
                        .documentID((String) row.get("DOCUMENT_ID"))
                        .demand(demandService.findByID((String) row.get("DEMAND_ID")))
                        .pdf(Paths.get((String) row.get("PATH")).toFile())
                        .creationDate(LocalDate.parse((String) row.get("CREATION_DATE")))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(OfficialDocument officialDocument) {
        OfficialDocumentDAO dao = OfficialDocumentDAO.builder()
                .documentID(UUID.randomUUID().toString())
                .creationDate(LocalDate.now())
                .path(officialDocument.getPdf().getAbsolutePath())
                .demandID(officialDocument.getDemand().getDemandID())
                .build();
        jdbcTemplate.update("INSERT INTO OFFICIAL_DOC (DOCUMENT_ID,CREATION_DATE,PATH, DEMAND_ID ) VALUES (?,?,?,?)",
                dao.getDocumentID(),
                dao.getCreationDate(),
                dao.getPath(),
                dao.getDemandID());
        LOGGER.debug("officialDoc {} has been added", dao.toString());
    }

}

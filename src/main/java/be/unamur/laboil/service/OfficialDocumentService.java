package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.OfficialDocument;
import be.unamur.laboil.domain.persistance.OfficialDocumentDAO;
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
public class OfficialDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficialDocumentService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private TownService townService;

    @Autowired
    private DemandService demandService;


    public OfficialDocument findByID(String docID) {
        OfficialDocumentDAO dao = jdbcTemplate.queryForObject("SELECT * FROM OFFICIAL_DOC where DOCUMENT_ID = ?",
                new Object[]{docID}, (resultSet, i) -> OfficialDocumentDAO.builder()
                        .beneficiary(resultSet.getString("DOCUMENT_BENEFICIARY"))
                        .creationDate(resultSet.getDate("DOCUMENT_DATE").toLocalDate())
                        .expirationDate(resultSet.getDate("DOCUMENT_EXPIRY").toLocalDate())
                        .documentID(resultSet.getString("DOCUMENT_ID"))
                        .issuerService(resultSet.getString("DOCUMENT_ISSUER_SERVICE"))
                        .issuerTown(resultSet.getString("DOCUMENT_ISSUER_TOWN"))
                        .tracking(resultSet.getString("DOCUMENT_TRACKING"))
                        .build());


        return OfficialDocument.builder()
                .beneficiary(citizenService.findById(dao.getBeneficiary()))
                .creationDate(dao.getCreationDate())
                .expiration(dao.getExpirationDate())
                .documentID(dao.getDocumentID())
                .issuerService(serviceService.findById(dao.getIssuerService()))
                .issuerTown(townService.findById(dao.getIssuerTown()))
                .tracking(demandService.findByID(dao.getTracking()))
                .build();


    }


    public List<OfficialDocument> getAllOfficialDocuments() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM OFFICIAL_DOC");
        return rows
                .stream()
                .map(row -> OfficialDocument.builder()
                        .beneficiary(citizenService.findById((String) row.get("DOCUMENT_BENEFICIARY")))
                        .creationDate(LocalDate.parse((String) row.get("DOCUMENT_DATE")))
                        .expiration(LocalDate.parse((String) row.get("DOCUMENT_EXPIRY")))
                        .documentID((String) row.get("DOCUMENT_ID"))
                        .issuerService(serviceService.findById((String) row.get("DOCUMENT_ISSUER_SERVICE")))
                        .issuerTown(townService.findById((String) row.get("DOCUMENT_ISSUER_TOWN")))
                        .tracking(demandService.findByID((String) row.get("DOCUMENT_TRACKING")))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(OfficialDocument officialDocument) {
        OfficialDocumentDAO dao = OfficialDocumentDAO.builder()
                .tracking(officialDocument.getTracking().getDemandID())
                .issuerTown(officialDocument.getIssuerTown().getTownID())
                .issuerService(officialDocument.getIssuerService().getServiceID())
                .documentID(UUID.randomUUID().toString())
                .expirationDate(officialDocument.getExpiration())
                .creationDate(officialDocument.getCreationDate())
                .beneficiary(officialDocument.getBeneficiary().getUserID())
                .build();
        jdbcTemplate.update("INSERT INTO OFFICIAL_DOC (DOCUMENT_BENEFICIARY,DOCUMENT_DATE,DOCUMENT_EXPIRY,DOCUMENT_ID,DOCUMENT_ISSUER_SERVICE,DOCUMENT_ISSUER_TOWN, DOCUMENT_TRACKING ) VALUES (?,?,?,?,?,?,?)",
                dao.getBeneficiary(),
                dao.getCreationDate(),
                dao.getExpirationDate(),
                dao.getDocumentID(),
                dao.getIssuerService(),
                dao.getIssuerTown(),
                dao.getTracking());
        LOGGER.debug("officialDoc {} has been added", dao.toString());
    }

}

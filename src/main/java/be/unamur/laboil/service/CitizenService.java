package be.unamur.laboil.service;

import be.unamur.laboil.component.Encryptor;
import be.unamur.laboil.domain.core.Citizen;
import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.persistance.CitizenDAO;
import be.unamur.laboil.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class CitizenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CitizenService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TownService townService;

    @Autowired
    private Encryptor encryptor;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public Citizen findById(String userId) {
        CitizenDAO dao = jdbcTemplate.queryForObject("SELECT c.*,u.* FROM CITIZEN c JOIN USER u on u.USER_ID=c.USER_ID where u.USER_ID = ?",
                new Object[]{userId}, (resultSet, i) -> extractFromRS(resultSet));

        return buildFromDAO(dao);
    }

    private Citizen buildFromDAO(CitizenDAO dao) {
        return Citizen.builder()
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .address(dao.getAddress())
                .town(townService.findById(dao.getTown()))
                .phoneNumber(dao.getPhoneNumber())
                .birthDate(LocalDate.parse(encryptor.decrypt(dao.getBirthDate()), Constants.SQL_FORMATTER))
                .email(dao.getEmail())
                .natReg(encryptor.decrypt(dao.getNatReg()))
                .citizenID(dao.getCitizenID())
                .userID(dao.getUserID()).build();
    }

    public Citizen findByEmail(String email) {
        CitizenDAO dao = jdbcTemplate.queryForObject("SELECT c.*,u.* FROM CITIZEN c JOIN USER u on u.USER_ID=c.USER_ID where u.EMAIL = ?",
                new Object[]{email}, (resultSet, i) -> extractFromRS(resultSet));
        return buildFromDAO(dao);
    }

    private CitizenDAO extractFromRS(ResultSet resultSet) throws SQLException {
        return CitizenDAO.builder()
                .firstName(resultSet.getString("FIRST_NAME"))
                .lastName(resultSet.getString("LAST_NAME"))
                .address(resultSet.getString("ADDRESS"))
                .town(resultSet.getString("TOWN"))
                .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                .birthDate(resultSet.getString("BIRTH_DATE"))
                .email(resultSet.getString("EMAIL"))
                .userID(resultSet.getString("USER_ID"))
                .natReg(resultSet.getString("REG_NAT"))
                .citizenID(resultSet.getString("CITIZEN_ID"))
                .build();
    }

    public List<Citizen> getAllCitizens() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT c.*,u.* FROM CITIZEN JOIN USER u on u.USER_ID=c.USER_ID");
        return rows
                .stream()
                .map(row -> Citizen.builder()
                        .firstName((String) row.get("FIRST_NAME"))
                        .lastName((String) row.get("LAST_NAME"))
                        .address((String) row.get("ADDRESS"))
                        .town(townService.findById((String) row.get("TOWN")))
                        .phoneNumber((String) row.get("PHONE_NUMBER"))
                        .citizenID((String) row.get("CITIZEN_ID"))
                        .birthDate(LocalDate.parse(encryptor.decrypt((String) row.get("BIRTH_DATE"))))
                        .email((String) row.get("EMAIL"))
                        .userID((String) row.get("USER_ID"))
                        .natReg(encryptor.decrypt((String) row.get("REG_NAT")))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(Citizen citizen) {
        Town town = townService.findByName(citizen.getTown().getName());
        CitizenDAO dao = CitizenDAO.builder()
                .citizenID(UUID.randomUUID().toString())
                .address(citizen.getAddress())
                .birthDate(encryptor.encrypt(citizen.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE)))
                .email(citizen.getEmail())
                .firstName(citizen.getFirstName())
                .town(town.getTownID())
                .lastName(citizen.getLastName())
                .natReg(encryptor.encrypt(citizen.getNatReg()))
                .userID(UUID.randomUUID().toString())
                .password(bCryptPasswordEncoder.encode(citizen.getPassword()))
                .phoneNumber(citizen.getPhoneNumber()).build();
        jdbcTemplate.update("INSERT INTO USER " +
                        "(USER_ID,FIRST_NAME,LAST_NAME,ADDRESS,PHONE_NUMBER,BIRTH_DATE,EMAIL,PASSWORD,REG_NAT,ENABLED) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                dao.getUserID(),
                dao.getFirstName(),
                dao.getLastName(),
                dao.getAddress(),
                dao.getPhoneNumber(),
                dao.getBirthDate(),
                dao.getEmail(),
                dao.getPassword(),
                dao.getNatReg(),
                true);
        jdbcTemplate.update("INSERT INTO ROLE " +
                        "(EMAIL,RIGHTS) " +
                        "VALUES (?,?)",
                dao.getEmail(), "CITIZEN");

        jdbcTemplate.update("INSERT INTO CITIZEN " +
                        "(CITIZEN_ID,USER_ID,TOWN) " +
                        "VALUES (?,?,?)",
                dao.getCitizenID(),
                dao.getUserID(),
                dao.getTown());
        LOGGER.debug("citizen {} has been added", dao.toString());
    }

}

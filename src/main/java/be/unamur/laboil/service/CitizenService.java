package be.unamur.laboil.service;

import be.unamur.laboil.component.Encryptor;
import be.unamur.laboil.domain.core.Citizen;
import be.unamur.laboil.domain.core.Gender;
import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.persistance.CitizenDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        CitizenDAO dao = jdbcTemplate.queryForObject("SELECT * FROM CITIZEN where USER_ID = ?",
                new Object[]{userId}, (resultSet, i) -> CitizenDAO.builder()
                        .firstName(resultSet.getString("FIRST_NAME"))
                        .lastName(resultSet.getString("LAST_NAME"))
                        .address(resultSet.getString("ADDRESS"))
                        .town(resultSet.getString("TOWN"))
                        .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                        .birthDate(resultSet.getString("BIRTH_DATE"))
                        .email(resultSet.getString("EMAIL"))
                        .gender(resultSet.getString("GENDER"))
                        .birthPlace(resultSet.getString("BIRTH_PLACE"))
                        .userID(resultSet.getString("USER_ID"))
                        .natReg(resultSet.getString("REG_NAT"))
                        .build());

        return Citizen.builder()
                .firstName(dao.getFirstName())
                .lastName(dao.getLastName())
                .address(dao.getAddress())
                .town(townService.findById(dao.getTown()))
                .phoneNumber(dao.getPhoneNumber())
                .birthDate(LocalDate.parse(encryptor.decrypt(dao.getBirthDate())))
                .email(dao.getEmail())
                .gender(Gender.fromAbbr(dao.getGender()))
                .birthPlace(dao.getBirthPlace())
                .natReg(encryptor.decrypt(dao.getNatReg()))
                .userID(dao.getUserID()).build();
    }


    public List<Citizen> getAllCitizens() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM CITIZEN");
        return rows
                .stream()
                .map(row -> Citizen.builder()
                        .firstName((String) row.get("FIRST_NAME"))
                        .lastName((String) row.get("LAST_NAME"))
                        .address((String) row.get("ADDRESS"))
                        .town(townService.findById((String) row.get("TOWN")))
                        .phoneNumber((String) row.get("PHONE_NUMBER"))
                        .birthDate(LocalDate.parse(encryptor.decrypt((String) row.get("BIRTH_DATE"))))
                        .email((String) row.get("EMAIL"))
                        .gender(Gender.fromAbbr((String) row.get("GENDER")))
                        .birthPlace((String) row.get("BIRTH_PLACE"))
                        .userID((String) row.get("USER_ID"))
                        .natReg(encryptor.decrypt((String) row.get("REG_NAT")))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(Citizen citizen) {
        Town town = townService.findByName(citizen.getTown().getName());
        CitizenDAO dao = CitizenDAO.builder()
                .address(citizen.getAddress())
                .birthDate(encryptor.encrypt(citizen.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE)))
                .birthPlace(citizen.getBirthPlace())
                .email(citizen.getEmail())
                .firstName(citizen.getFirstName())
                .gender(citizen.getGender().getAbbr())
                .town(town.getTownID())
                .lastName(citizen.getLastName())
                .natReg(encryptor.encrypt(citizen.getNatReg()))
                .userID(UUID.randomUUID().toString())
                .password(bCryptPasswordEncoder.encode(citizen.getPassword()))
                .phoneNumber(citizen.getPhoneNumber()).build();
        jdbcTemplate.update("INSERT INTO CITIZEN " +
                        "(FIRST_NAME,LAST_NAME,ADDRESS,BIRTH_DATE,BIRTH_PLACE,EMAIL,GENDER,TOWN,USER_ID,PASSWORD,REG_NAT) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                dao.getFirstName(),
                dao.getLastName(),
                dao.getAddress(),
                dao.getBirthDate(),
                dao.getBirthPlace(),
                dao.getEmail(),
                dao.getGender(),
                dao.getTown(),
                dao.getUserID(),
                dao.getPassword(),
                dao.getNatReg());
        jdbcTemplate.update("INSERT INTO AUTHORITIES " +
                        "(EMAIL,AUTHORITY) " +
                        "VALUES (?,?)",
                dao.getEmail(), "CITIZEN");
        LOGGER.debug("citizen {} has been added", dao.toString());
    }

}

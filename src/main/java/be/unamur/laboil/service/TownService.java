package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.persistance.TownDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class TownService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Town findById(String townID) {
        TownDAO dao = jdbcTemplate.queryForObject("SELECT * FROM TOWN where TOWN_ID = ?",
                new Object[]{townID}, (resultSet, i) -> extractFromRS(resultSet));

        return buildFromDAO(dao);
    }

    private Town buildFromDAO(TownDAO dao) {
        return Town.builder()
                .name(dao.getName())
                .country(dao.getCountry())
                .language(dao.getLanguage())
                .postalCode(dao.getPostalCode())
                .townID(dao.getTownID())
                .address(dao.getAddress())
                .country(dao.getCountry())
                .email(dao.getEmail()   )
                .logo(Paths.get(dao.getLogoPath()).toFile())
                .phoneNumber(dao.getPhoneNumber())
                .build();
    }

    private TownDAO extractFromRS(ResultSet resultSet) throws SQLException {
        return TownDAO.builder()
                .townID(resultSet.getString("TOWN_ID"))
                .name(resultSet.getString("NAME"))
                .language(resultSet.getString("TOWN_LANGUAGE"))
                .postalCode(resultSet.getInt("POSTAL_CODE"))
                .address(resultSet.getString("ADDRESS"))
                .country(resultSet.getString("COUNTRY"))
                .email(resultSet.getString("EMAIL"))
                .logoPath(resultSet.getString("LOGO_PATH"))
                .phoneNumber(resultSet.getString("PHONE_NUMBER"))
                .build();
    }

    public Town findByName(String townName) {
        TownDAO dao = jdbcTemplate.queryForObject("SELECT * FROM TOWN where NAME = ?",
                new Object[]{townName}, (resultSet, i) -> extractFromRS(resultSet));
        return buildFromDAO(dao);
    }

    public List<Town> getAllTowns() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM TOWN");
        return rows
                .stream()
                .map(row -> Town.builder()
                        .name((String) row.get("TOWN_NAME"))
                        .country((String) row.get("COUNTRY"))
                        .language((String) row.get("TOWN_LANGUAGE"))
                        .postalCode((int) row.get("POSTAL_CODE"))
                        .townID((String) row.get("TOWN_ID"))
                        .build())
                .collect(Collectors.toList());
    }
}

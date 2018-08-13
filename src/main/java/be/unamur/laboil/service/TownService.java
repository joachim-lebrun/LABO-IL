package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.OfficialDocument;
import be.unamur.laboil.domain.core.Town;
import be.unamur.laboil.domain.persistance.OfficialDocumentDAO;
import be.unamur.laboil.domain.persistance.TownDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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
                new Object[]{townID}, (resultSet, i) -> TownDAO.builder()
                        .name(resultSet.getString("TOWN_NAME"))
                        .country(resultSet.getString("COUNTRY"))
                        .language(resultSet.getString("TOWN_LANGUAGE"))
                        .postalCode(resultSet.getInt("POSTAL_CODE"))
                        .townID(resultSet.getString("TOWN_ID"))
                        .build());

        return Town.builder()
                .name(dao.getName())
                .country(dao.getCountry())
                .language(dao.getLanguage())
                .postalCode(dao.getPostalCode())
                .townID(dao.getTownID())
                .build();
    }

    public Town findByName(String townName) {
        TownDAO dao = jdbcTemplate.queryForObject("SELECT * FROM TOWN where TOWN_NAME = ?",
                new Object[]{townName}, (resultSet, i) -> TownDAO.builder()
                        .name(resultSet.getString("TOWN_NAME"))
                        .country(resultSet.getString("COUNTRY"))
                        .language(resultSet.getString("TOWN_LANGUAGE"))
                        .postalCode(resultSet.getInt("POSTAL_CODE"))
                        .townID(resultSet.getString("TOWN_ID"))
                        .build());

        return Town.builder()
                .name(dao.getName())
                .country(dao.getCountry())
                .language(dao.getLanguage())
                .postalCode(dao.getPostalCode())
                .townID(dao.getTownID())
                .build();
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

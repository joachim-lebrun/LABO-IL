package be.unamur.laboil.service;

import be.unamur.laboil.domain.core.Company;
import be.unamur.laboil.domain.persistance.CompanyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Service
public class CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TownService townService;


    public Company findByVAT(String vatNum) {
        CompanyDAO dao = jdbcTemplate.queryForObject("SELECT * FROM COMPANY where COMPANY_VAT_NR = ?",
                new Object[]{vatNum}, (resultSet, i) -> CompanyDAO.builder()
                        .address(resultSet.getString("COMPANY_ADDRESS"))
                        .name(resultSet.getString("COMPANY_NAME"))
                        .phone(resultSet.getString("COMPANY_PHONE"))
                        .vatNumber(resultSet.getString("COMPANY_VAT_NR"))
                        .town(resultSet.getString("COMPANY_TOWN"))
                        .build());

        return Company.builder()
                .address(dao.getAddress())
                .name(dao.getName())
                .phone(dao.getPhone())
                .vatNumber(dao.getVatNumber())
                .town(townService.findById(dao.getTown()))
                .build();


    }


    public List<Company> getAllCompanies() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM COMPANY");
        return rows
                .stream()
                .map(row -> Company.builder()
                        .town(townService.findById((String) row.get("COMPANY_TOWN")))
                        .vatNumber((String) row.get("COMPANY_VAT_NR"))
                        .phone((String) row.get("COMPANY_PHONE"))
                        .name((String) row.get("COMPANY_NAME"))
                        .address((String) row.get("COMPANY_ADDRESS"))
                        .build())
                .collect(Collectors.toList());
    }

    public void insert(Company company) {
        CompanyDAO dao = CompanyDAO.builder()
                .town(company.getTown().getTownID())
                .vatNumber(company.getVatNumber())
                .phone(company.getPhone())
                .name(company.getName())
                .address(company.getAddress())
                .build();
        jdbcTemplate.update("INSERT INTO COMPANY (COMPANY_TOWN,COMPANY_VAT_NR,COMPANY_PHONE,COMPANY_NAME,COMPANY_ADDRESS) VALUES (?,?,?,?,?)",
                dao.getTown(),
                dao.getVatNumber(),
                dao.getPhone(),
                dao.getName(),
                dao.getAddress());
        LOGGER.debug("company {} has been added", dao.toString());
    }

}

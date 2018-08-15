package be.unamur.laboil.domain.persistance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDAO {
    private String serviceID;
    private String name;
    private String town;
    private String address;

}

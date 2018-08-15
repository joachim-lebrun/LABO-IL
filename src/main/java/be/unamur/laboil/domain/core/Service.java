package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    private String serviceID;
    private String name;
    private String address;
    private Town town;

}

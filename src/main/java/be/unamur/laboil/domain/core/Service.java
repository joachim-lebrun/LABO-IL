package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    private String serviceID;
    private String name;
    private String address;
    private Employee administrator;
    private Town town;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("serviceID", serviceID)
                .append("name", name)
                .toString();
    }
}

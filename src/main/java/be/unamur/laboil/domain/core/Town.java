package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Town {

    private String townID;
    private String name;
    private String language;
    private String address;
    private String email;
    private String phoneNumber;
    private Employee mayor;
    private File logo;
    private String country;
    private int postalCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("townID", townID)
                .append("name", name)
                .toString();
    }
}

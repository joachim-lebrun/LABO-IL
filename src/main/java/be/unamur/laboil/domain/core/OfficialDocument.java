package be.unamur.laboil.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDate;

/**
 * @author Joachim Lebrun on 06-08-18
 */

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficialDocument {

    private LocalDate creationDate;
    private String documentID;
    private Demand demand;
    private File pdf;

}

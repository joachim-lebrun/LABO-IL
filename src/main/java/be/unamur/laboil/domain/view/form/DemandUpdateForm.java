package be.unamur.laboil.domain.view.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joachim Lebrun on 21-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandUpdateForm {

    private String comment;
    private String amount;
    private String communalName;
}

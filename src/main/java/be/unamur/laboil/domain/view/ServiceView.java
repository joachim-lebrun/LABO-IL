package be.unamur.laboil.domain.view;

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
public class ServiceView {

    private String name;
    private String id;

}

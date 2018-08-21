package be.unamur.laboil.domain.view.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandForm {

    private String name;
    private String serviceId;
    private List<File> linkedDocuments;
    private String comment;
    private String userID;
    private String placeSize;
    private String address;
    private String startDate;
    private String endDate;
}

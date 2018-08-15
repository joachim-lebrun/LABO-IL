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
public class OfficialDocumentView {

    private String createdDate;
    private String documentID;

}

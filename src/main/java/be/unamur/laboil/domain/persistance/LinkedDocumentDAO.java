package be.unamur.laboil.domain.persistance;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joachim Lebrun on 15-08-18
 */
@Data
public class LinkedDocumentDAO {
    private String linkedDocumentID;
    private String demandID;
    private String path;
    private String name;

    public LinkedDocumentDAO() {
    }

    @Builder
    public LinkedDocumentDAO(String linkedDocumentID, String demandID, String path, String name) {
        this.linkedDocumentID = linkedDocumentID;
        this.demandID = demandID;
        this.path = path;
        this.name = name;
    }
}

package be.unamur.laboil.manager;

import be.unamur.laboil.domain.core.Demand;
import be.unamur.laboil.domain.core.OfficialDocument;
import be.unamur.laboil.domain.view.DemandView;
import be.unamur.laboil.domain.view.form.OfficialDocumentForm;
import be.unamur.laboil.domain.view.OfficialDocumentView;
import be.unamur.laboil.service.CitizenService;
import be.unamur.laboil.service.DemandService;
import be.unamur.laboil.service.EmployeeService;
import be.unamur.laboil.service.OfficialDocumentService;
import be.unamur.laboil.service.ServiceService;
import be.unamur.laboil.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Component
public class DocumentManager {

    @Autowired
    private DemandService demandService;
    @Autowired
    private OfficialDocumentService officialDocumentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private TownService townService;

    public List<DemandView> getAllDemands() {
        return demandService
                .getAllDemands()
                .stream()
                .map(demand -> DemandView.builder()
                        .accepted(demand.getAccepted())
                        .checker(demand.getChecker().getUserID())
                        .creationDate(demand.getCreationDate())
                        .creator(demand.getCreator().getUserID())
                        .demandID(demand.getDemandID())
                        .sendingDate(demand.getSendingDate())
                        .signedByCreator(demand.getSignedByCreator())
                        .signedByValidator(demand.getSignedByValidator())
                        .verificationDate(demand.getVerificationDate())
                        .build())
                .collect(Collectors.toList());
    }

    public void add(DemandView demandView) {
        Demand newDemand = Demand.builder()
                .demandID(demandView.getDemandID())
                .accepted(demandView.getAccepted())
                .checker(employeeService.findById(demandView.getChecker()))
                .creationDate(demandView.getCreationDate())
                .creator(citizenService.findById(demandView.getCreator()))
                .sendingDate(demandView.getSendingDate())
                .signedByCreator(demandView.getSignedByCreator())
                .signedByValidator(demandView.getSignedByValidator())
                .verificationDate(demandView.getVerificationDate())
                .build();
        demandService.insert(newDemand);
    }

    public List<OfficialDocumentView> getAllOfficialDocuments() {
        return officialDocumentService
                .getAllOfficialDocuments()
                .stream()
                .map(officialDocument -> OfficialDocumentView.builder()
                        .documentBeneficiary(officialDocument.getBeneficiary().getUserID())
                        .documentDate(officialDocument.getCreationDate())
                        .documentExpiration(officialDocument.getExpiration())
                        .documentID(officialDocument.getDocumentID())
                        .documentIssuerService(officialDocument.getIssuerService().getName())
                        .documentIssuerTown(officialDocument.getIssuerTown().getName())
                        .documentTracking(officialDocument.getTracking().getDemandID())
                        .build())
                .collect(Collectors.toList());
    }

    public void add(OfficialDocumentForm officialDocumentView) {
        OfficialDocument newOfficialDocument = OfficialDocument.builder()
                .beneficiary(citizenService.findById(officialDocumentView.getDocumentBeneficiary()))
                .creationDate(officialDocumentView.getDocumentDate())
                .expiration(officialDocumentView.getDocumentExpiration())
                .documentID(officialDocumentView.getDocumentID())
                .issuerService(serviceService.findById(officialDocumentView.getDocumentIssuerService()))
                .issuerTown(townService.findById(officialDocumentView.getDocumentIssuerTown()))
                .tracking(demandService.findByID(officialDocumentView.getDocumentTracking()))
                .build();
        officialDocumentService.insert(newOfficialDocument);
    }
}

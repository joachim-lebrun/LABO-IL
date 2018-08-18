package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.EmployeeView;
import be.unamur.laboil.domain.view.SimpleDemandView;
import be.unamur.laboil.manager.UserManager;
import be.unamur.laboil.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Controller
public class EmployeeController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping({"/employees/me"})
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        EmployeeView citizenWithEmail = userManager.getEmployeeWithEmail(user.getUsername());
        model.addAttribute("employee", citizenWithEmail);
        model.addAttribute("employeeUUID", citizenWithEmail.getUserID());
        return "pages/userInfo";
    }

    @GetMapping({"/employees/me/service/demands"})
    public String myDemands(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<SimpleDemandView> demandViewList = userManager.getPendingDemands(user.getUsername());
        model.addAttribute("demands", demandViewList);
        return "pages/serviceDashboard";
    }


    @GetMapping({"/demands/{id}/officialDocument"})
    public ResponseEntity<byte[]> generate(@PathVariable("id") String demandID) throws IOException {
        FileSystemResource fileSystemResource = userManager.generatePDF(demandID);
        byte[] contents = Files.readAllBytes(Paths.get(fileSystemResource.getPath()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = fileSystemResource.getFilename();
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }
}

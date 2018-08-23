package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.EmployeeView;
import be.unamur.laboil.domain.view.SimpleDemandView;
import be.unamur.laboil.domain.view.form.DemandUpdateForm;
import be.unamur.laboil.manager.UserManager;
import be.unamur.laboil.service.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        List<SimpleDemandView> demandViewList = userManager.getServicePendingDemands(user.getUsername());
        model.addAttribute("demands", demandViewList);
        model.addAttribute("demandUploadForm", DemandUpdateForm.builder().build());
        return "pages/serviceDashboard";
    }


    @PostMapping({"/employees/service/demands/{id}/validate"})
    public String validate(@PathVariable("id") String demandID, @ModelAttribute DemandUpdateForm demandUpdateForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userManager.validateDemand(demandID, user.getUsername(), demandUpdateForm);
        return "redirect:/employees/me/service/demands";
    }

    @PostMapping({"/employees/service/demands/{id}/refuse"})
    public String refuse(@PathVariable("id") String demandID, @ModelAttribute DemandUpdateForm demandUpdateForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userManager.refuseDemand(demandID, user.getUsername(), demandUpdateForm);
        return "redirect:/employees/me/service/demands";
    }

    @PostMapping({"/employees/service/demands/{id}/comment"})
    public String comment(@PathVariable("id") String demandID, @ModelAttribute DemandUpdateForm demandUpdateForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userManager.commentFromEmployee(demandID, user.getUsername(), demandUpdateForm);
        return "redirect:/employees/me/service/demands";
    }
}

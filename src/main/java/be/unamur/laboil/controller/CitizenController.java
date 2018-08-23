package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.domain.view.form.DemandForm;
import be.unamur.laboil.domain.view.form.DemandUpdateForm;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Controller
public class CitizenController {

    @Autowired
    private UserManager userManager;

    @GetMapping({"/citizens/me"})
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CitizenView citizenWithEmail = userManager.getCitizenWithEmail(user.getUsername());
        model.addAttribute("citizen", citizenWithEmail);
        model.addAttribute("citizenUUID", citizenWithEmail.getUserID());
        return "pages/userInfo";
    }

    @GetMapping({"/citizens/me/demands"})
    public String myDemands(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("demands", userManager.getMyDemands(user.getUsername()));
        model.addAttribute("services", userManager.getAllServicesOfMyTown(user.getUsername()));
        model.addAttribute("demandForm", DemandForm.builder().build());
        model.addAttribute("demandUploadForm", DemandForm.builder().build());
        return "pages/citizensDemands";
    }


    @PostMapping({"/citizens/me/demands"})
    public String newDemands(@ModelAttribute DemandForm demandForm, final BindingResult bindingResult, final ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (bindingResult.hasErrors()) {
            return "#";
        } else {
            this.userManager.createDemand(demandForm, user.getUsername());
            model.clear();
            return "redirect:/citizens/me/demands";
        }
    }


    @PostMapping({"/citizens/me/demands/{id}/comment"})
    public String comment(@PathVariable("id") String demandID, @ModelAttribute DemandUpdateForm demandUpdateForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userManager.commentFromCitizen(demandID, user.getUsername(), demandUpdateForm);
        return "redirect:/citizens/me/demands";
    }
}

package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String myDemands(Model model, @PathVariable("id") String userID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        if (userManager.validate(email, userID)) {
            model.addAttribute("demands", userManager.getMyDemands(userID));
            return "userDemands";
        } else {
            return "login";
        }
    }
}

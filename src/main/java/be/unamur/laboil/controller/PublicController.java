package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Controller
public class PublicController {

    @Autowired
    private UserManager userManager;

    @PostMapping("/signup")
    public String saveCitizen(@ModelAttribute CitizenView citizenView, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            this.userManager.add(citizenView);
            model.clear();
            return "redirect:/";
        }
    }
}

package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Controller
public class LoginController {

    @Autowired
    private UserManager userManager;

    @GetMapping({"/login"})
    public String login() {
        return "pages/login";
    }

    @GetMapping({"/userType"})
    public String userType() {
        return "pages/userType";
    }

    @RequestMapping({"/signup"})
    public String signup(Model model) {
        model.addAttribute("citizenView", CitizenView.builder().build());
        return "pages/signup";
    }

}

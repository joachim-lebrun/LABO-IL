package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.manager.SystemManager;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Joachim Lebrun on 06-08-18
 */
@Controller
public class MenuController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private SystemManager systemManager;

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute("citizenView", CitizenView.builder().build());
        return "pages/index";
    }
}

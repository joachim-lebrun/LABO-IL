package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.domain.view.SimpleDemandView;
import be.unamur.laboil.domain.view.EmployeeView;
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

    @GetMapping({"/citizen/all"})
    public String citizens(Model model) {
        model.addAttribute("citizenView", CitizenView.builder().build());
        model.addAttribute("citizens", userManager.getAllCitizens());
        model.addAttribute("towns", systemManager.getAllTowns());
        return "pages/citizens";
    }

    @GetMapping({"/citizen/demands"})
    public String demands(Model model) {
        model.addAttribute("demandView", SimpleDemandView.builder().build());
        return "pages/demands";
    }

    @GetMapping({"/employee/all"})
    public String employees(Model model) {
        model.addAttribute("employeeView", EmployeeView.builder().build());
        return "pages/employees";
    }


}

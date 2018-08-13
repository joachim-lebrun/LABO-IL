package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.CitizenView;
import be.unamur.laboil.domain.view.form.EmployeeForm;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HomeController {

    @Autowired
    private UserManager userManager;

    @GetMapping({"/user/{id}"})
    public String index(Model model, @PathVariable("id") String userID) {
        model.addAttribute("citizen", userManager.getCitizen(userID));
        return "userInfo";
    }


    @PostMapping("/addCitizen")
    public String saveCitizen(@ModelAttribute CitizenView citizenView, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else {
            this.userManager.add(citizenView);
            model.clear();
            return "redirect:/";
        }
    }

    @PostMapping("/addEmployee")
    public String saveEmployee(@ModelAttribute EmployeeForm employeeForm, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "addEmployee";
        } else {
            this.userManager.add(employeeForm);
            model.clear();
            return "redirect:/";
        }
    }

    /*@ModelAttribute("citizens")
    public List<CitizenView> populateUsers() {
        return userManager.getAllCitizens();
    }*/

}

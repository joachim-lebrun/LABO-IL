package be.unamur.laboil.controller;

import be.unamur.laboil.domain.view.TownView;
import be.unamur.laboil.manager.SystemManager;
import be.unamur.laboil.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * @author Joachim Lebrun on 10-08-18
 */
@Controller
public class GenericController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private SystemManager systemManager;

    @ModelAttribute("towns")
    public List<TownView> populateTowns() {
        return systemManager.getAllTowns();
    }
}

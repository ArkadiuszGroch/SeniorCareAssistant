package pl.edu.pwste.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Notification;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Service.CareService;
import pl.edu.pwste.Service.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Scope("session")
@Controller
@RequestMapping("/careAssistant")
public class CareAssistantController {

    @Autowired
    CareService careService;
    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/dashboard/chooseSenior")
    public ModelAndView chooseSenior(ModelAndView modelAndView, HttpSession session) {
        CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
        List<Senior> listOfSeniors = careService.findAllSeniorsForCareAssistant(loggedUser);
        List<String> listOfNameSeniors = new ArrayList<>();
        for (Senior senior : listOfSeniors) {
            listOfNameSeniors.add("(" + senior.getUser().getLogin() + ")" + senior.getUser().getFirstName() + " " + senior.getUser().getLastName());
        }
        if (loggedUser != null) {
            modelAndView.setViewName("chooseSenior");
            modelAndView.addObject("user", loggedUser.getUser());
            if (!listOfSeniors.isEmpty()) {
                modelAndView.addObject("listOfSeniors", listOfNameSeniors);
            } else {
                //TODO empty list, add senior to list
            }
        } else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(value = "/dashboard")
    public ModelAndView showMainPage(ModelAndView modelAndView, CareAssistant careAssistant, HttpSession session) {
        CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
        List<Senior> listOfSeniors = careService.findAllSeniorsForCareAssistant(loggedUser);
        List<String> listOfNameSeniors = new ArrayList<>();
        for (Senior senior : listOfSeniors) {
            listOfNameSeniors.add("(" + senior.getUser().getLogin() + ")" + senior.getUser().getFirstName() + " " + senior.getUser().getLastName());
        }
        if (loggedUser != null) {
            modelAndView.addObject("user", loggedUser.getUser());
            if (!listOfSeniors.isEmpty())
                modelAndView.addObject("listOfSeniors", listOfNameSeniors);
        } else
            modelAndView.setViewName("index");

        return modelAndView;
    }
}

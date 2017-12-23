package pl.edu.pwste.Controller.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Service.CareService;
import pl.edu.pwste.Service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManageSeniorsController {

    @Autowired
    CareService careService;

    @Autowired
    UserService userService;

    @RequestMapping
    public ModelAndView manageSeniors(ModelAndView modelAndView, HttpSession session) {
        CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");

        List<Senior> listOfSeniors = careService.findAllSeniorsForCareAssistant(loggedUser);

        if (loggedUser != null) {
            modelAndView.setViewName("manageSeniors");
            modelAndView.addObject("user", loggedUser.getUser());
            if (!listOfSeniors.isEmpty()) {
                modelAndView.addObject("listOfSeniors", listOfSeniors);
            }
        } else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping("/remove/{seniorLogin}")
    public ModelAndView removeSeniorFromList(ModelAndView modelAndView, HttpSession session, @PathVariable(value = "seniorLogin") String seniorLogin) {

        try {
            CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
            loggedUser.setCare(null);
            Senior seniorToRemove = userService.findSeniorByLogin(seniorLogin);

            Care careToRemove = careService.findCareBySeniorAndCareAssistant(seniorToRemove, loggedUser);
            careService.removeCare(careToRemove);

            List<Senior> listOfSeniors = careService.findAllSeniorsForCareAssistant(loggedUser);

            if (loggedUser != null) {
                modelAndView.setViewName("manageSeniors");
                modelAndView.addObject("user", loggedUser.getUser());
                if (!listOfSeniors.isEmpty()) {
                    modelAndView.addObject("listOfSeniors", listOfSeniors);
                }
            } else {
                modelAndView.setViewName("index");
            }

            modelAndView.addObject("message", "The senior " + seniorToRemove.getUser().getLogin() + " has been successfully removed");
        } catch (Exception e) {
            modelAndView.addObject("error", e.toString());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addSeniorToList(ModelAndView modelAndView, HttpSession session, @RequestParam(value = "loginOrEmail") String seniorLoginOrEmail) {

        try {
            CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
            loggedUser.setCare(null);
            Senior senior = userService.findSeniorByLogin(seniorLoginOrEmail);
            if (senior == null) {
                User user = userService.findUserByEmail(seniorLoginOrEmail);
                if (user != null) {
                    senior = userService.findSeniorByEmail(user.getEmail());
                }
            }

            if (senior != null && loggedUser != null) {
                Care newCare = new Care();
                newCare.setSenior(senior);
                careService.addSeniorToCareAssistant(senior.getUser().getLogin(), loggedUser.getUser().getLogin());
                modelAndView.addObject("messageSuccess", "The senior " + senior.getUser().getLogin() + " has been successfully added");
                modelAndView.setViewName("chooseSenior");
            } else {
                modelAndView.addObject("messageFail", "The senior " + seniorLoginOrEmail + " cant be found");
                modelAndView.setViewName("chooseSenior");
            }


        } catch (Exception e) {
            modelAndView.addObject("error", e.toString());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}

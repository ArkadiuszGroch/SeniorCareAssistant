package pl.edu.pwste.Controller.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Notification;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Service.CareService;
import pl.edu.pwste.Service.NotificationService;
import pl.edu.pwste.Service.UserService;

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
    UserService userService;
    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/dashboard/chooseSenior")
    public ModelAndView chooseSenior(ModelAndView modelAndView, HttpSession session) {
        CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");

        List<Senior> listOfSeniors = careService.findAllSeniorsForCareAssistant(loggedUser);

        if (loggedUser != null) {
            modelAndView.setViewName("chooseSenior");
            modelAndView.addObject("user", loggedUser.getUser());
            if (!listOfSeniors.isEmpty()) {
                modelAndView.addObject("listOfSeniors", listOfSeniors);
            }
        } else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping(value = "/dashboard/{seniorId}")
    public ModelAndView showNotifications(ModelAndView modelAndView, HttpSession session, @PathVariable(value = "seniorId") int seniorId) {
        try {
            CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
            Senior senior = userService.findSeniorById(seniorId);

            Care care = careService.findCareBySeniorAndCareAssistant(senior, loggedUser);

//Notifications
            List<Notification> listOfNotifications = notificationService.getNotificationForCare(care);
            modelAndView.addObject("listOfNotifications", listOfNotifications);
            modelAndView.addObject("numberOfNotifications", listOfNotifications.size());
            modelAndView.addObject("senior", senior);
            modelAndView.setViewName("seniorDashboard");
//Location
            //todo add list of date to locations and today locations
//Settings
            modelAndView.addObject("safeDistance", senior.getSafeDistance());
            modelAndView.addObject("locationUpdateFrequency", senior.getLocationUpdateFrequency());
            modelAndView.addObject("phone",senior.getUser().getPhone());

        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", e.toString());
        }
        return modelAndView;
    }
}

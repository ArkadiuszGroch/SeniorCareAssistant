package pl.edu.pwste.Controller.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.*;
import pl.edu.pwste.Service.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.List;

@Scope("session")
@Controller
@RequestMapping("/careAssistant")
public class DashboardController {

    @Autowired
    private CareService careService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ContactService contactService;

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
    public ModelAndView showDashboard(ModelAndView modelAndView, HttpSession session, @PathVariable(value = "seniorId") int seniorId) {
        try {
            session.setAttribute("senior", seniorId);

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
            modelAndView.addObject("locationUpdateFrequency", senior.getLocationUpdateFrequency().getTime());
            modelAndView.addObject("phone", senior.getUser().getPhone());
//Contacts
            List<Contact> contacts = contactService.getAllSeniorContact(senior.getUser().getLogin());
            modelAndView.addObject("contacts", contacts);
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", e.toString());
        }
        return modelAndView;
    }

    @PostMapping(value = "/dashboard/{seniorId}")
    public ModelAndView saveSettings(ModelAndView modelAndView, HttpSession session, @PathVariable(value = "seniorId") int seniorId,
                                     @RequestParam(value = "safeDistance") int safeDistance, @RequestParam(value = "locationUpdateFrequency") int locationUpdateFrequency,
                                     @RequestParam(value = "phone") int phone) {

        try {
            String errorMessage = null;
            CareAssistant loggedUser = (CareAssistant) session.getAttribute("user");
            session.setAttribute("senior", seniorId);
            Senior senior = userService.findSeniorById(seniorId);

            //check safe distance
            if (safeDistance >= 0)
                senior.setSafeDistance(safeDistance);
            else
                errorMessage = new String("Invalid value in safeDistance");

            //chceck locationUpdateFrequncy
            Time locationUpdateFrequencyTime = new Time(locationUpdateFrequency);//convertSecoundToTime(locationUpdateFrequency/1000);
            if (locationUpdateFrequencyTime != null && locationUpdateFrequency > 0) {
                senior.setLocationUpdateFrequency(locationUpdateFrequencyTime);
            } else {
                if (errorMessage == null) {
                    errorMessage = new String("Invalid value in locationUpdateFrequency");
                } else {
                    errorMessage += "\nInvalid value in locationUpdateFrequency";
                }
            }

            //check phone
            if (phone > 0) {
                senior.getUser().setPhone(String.valueOf(phone));
            } else {
                if (errorMessage == null) {
                    errorMessage = new String("Invalid value in phone nuber");
                } else {
                    errorMessage += "\nInvalid value in phone nuber";
                }
            }

            //check if senior must be updated
            if (errorMessage == null || errorMessage.isEmpty()) {
                accountService.updateSenior(senior);
            }
            showDashboard(modelAndView, session, seniorId);
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", e.toString());
        }
        return modelAndView;
    }

    private Time convertSecoundToTime(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        return Time.valueOf(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    @PostMapping(value = "/dashboard/removeContact")
    public ModelAndView removeContact(ModelAndView modelAndView, HttpSession session,
                                     @RequestParam(value = "contactToDelete") Integer contactToDelete) {
        try {
            contactService.deleteContact(contactToDelete);
            Integer seniorId = (Integer) session.getAttribute("senior");
            showDashboard(modelAndView, session, seniorId);

        } catch (Exception e) {
            modelAndView.addObject("error", e.toString());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}

package pl.edu.pwste.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Service.AccountService;
import pl.edu.pwste.Service.UserService;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginCareAssistant(ModelAndView modelAndView, BindingResult bindingResul, String user, String password) {
        CareAssistant careAssistant = accountService.findCareAssistantByLoginOrEmail(user, password);
        if (careAssistant == null) {
            modelAndView.addObject("careAssisntantNotFounddMessage", "A user with a login or email doesn't exist.");
            modelAndView.setViewName("login");
            System.out.println("A user with a login or email doesn't exist.");
        } else {
            if (careAssistant.getUser().getPassword().equals(password)) {
                modelAndView.setViewName("careAssistant");
                modelAndView.addObject(careAssistant);
                System.out.println("Login complete");
            } else {
                modelAndView.addObject("invalidPasswordFounddMessage", "Invalid password.");
                modelAndView.setViewName("login");
                System.out.println("Invalid password.");
            }
        }

        return modelAndView;
    }
}

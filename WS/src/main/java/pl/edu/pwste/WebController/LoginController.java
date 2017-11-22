package pl.edu.pwste.WebController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Service.AccountService;
import pl.edu.pwste.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public ModelAndView processLoginCareAssistant(ModelAndView modelAndView, BindingResult bindingResul, String user, String password, HttpSession session) {
        CareAssistant careAssistant = accountService.findCareAssistantByLoginOrEmail(user, user);
        if (careAssistant == null) {
            modelAndView.addObject("errorMessage", "Invalid login or email.");
            modelAndView.setViewName("login");
        } else {
            if (careAssistant.getUser().getPassword().equals(password)) {
                modelAndView.setViewName("careAssistant");
                session.setAttribute("user", careAssistant);
                System.out.println("Login complete");
            } else {
                modelAndView.addObject("errorMessage", "Invalid password.");
                modelAndView.setViewName("login");
                System.out.println("Invalid password.");
            }
        }

        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("user");
        return "redirect:/";
    }

}

package pl.edu.pwste.WebController;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pwste.Entity.CareAssistant;

import javax.servlet.http.HttpServletRequest;

@Scope("session")
@Controller
@RequestMapping("/careAssistant")
public class CareAssistantController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMainPage(ModelAndView modelAndView, CareAssistant careAssistant,HttpServletRequest request) {
        CareAssistant loggedUser = (CareAssistant) request.getAttribute("user");
        if(loggedUser != null)
            modelAndView.addObject("user", loggedUser.getUser());
        else
            modelAndView.setViewName("home");
        return modelAndView;
    }

}

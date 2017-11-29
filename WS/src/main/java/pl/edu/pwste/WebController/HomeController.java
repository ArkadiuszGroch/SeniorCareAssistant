package pl.edu.pwste.WebController;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@Scope("session")
@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String indexPage() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request) {
        if (request.getAttribute("user") == null)
            return "index";
        else
            return "careAssistant";
    }

}

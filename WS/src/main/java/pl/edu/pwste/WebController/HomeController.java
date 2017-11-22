package pl.edu.pwste.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage()
    {
        return "redirect:/home";
    }

    @RequestMapping("/index")
    public String indexPage()
    {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        return "index";
    }

}

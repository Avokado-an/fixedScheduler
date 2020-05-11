package OrganizerProject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingsController {
    @GetMapping
    public String showGreetingsPage() {
        return "greetings";
    }
}

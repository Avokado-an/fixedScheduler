package OrganizerProject.Controllers.UserRelated;

import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/activation/{username}")
public class ActivationController {
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public void setUserServiceImplementation(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @GetMapping
    public String activation(@PathVariable String username) {
        return "activation";
    }

    @PostMapping
    @Transactional
    public ModelAndView activate(
            @PathVariable String username,
            @RequestParam(name = "activationCode") String activationCode
    ) {
        User user = userServiceImplementation.loadUserByUsername(username);
        if (activationCode.equals(user.getActivationCode())) {
            user.setActive(true);
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/activation/" + username);
    }
}

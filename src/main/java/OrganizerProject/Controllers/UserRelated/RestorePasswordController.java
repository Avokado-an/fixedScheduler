package OrganizerProject.Controllers.UserRelated;

import OrganizerProject.ControllerTools.MailSender;
import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.UUID;

@Controller
@RequestMapping("/restorePassword")
public class RestorePasswordController {
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public void setUserServiceImplementation(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    MailSender mailSender = new MailSender();

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping
    public String restorePassword() {
        return "restorePassword";
    }

    @Transactional
    @PostMapping
    public ModelAndView restorePassword(
            @RequestParam String username
    ) {
        User user = userServiceImplementation.loadUserByUsername(username);
        String code = UUID.randomUUID().toString().substring(0, 4);
        mailSender.send(user.getEmail(), "Restore password", "Your code to change password - " + code);
        user.setActivationCode(code);
        return new ModelAndView("redirect:/restorePassword/" + username);
    }

    @GetMapping("/{username}")
    public String activateRestoringCode(@PathVariable String username) {
        return "activation";
    }

    @PostMapping("/{username}")
    public ModelAndView activateRestoringCode(
            @PathVariable String username,
            @RequestParam String activationCode,
            Model model
    ) {
        User user = userServiceImplementation.loadUserByUsername(username);
        if(activationCode.equals(user.getActivationCode()))
            return new ModelAndView("redirect:/restorePassword/changePassword/" + username);
        else {
            model.addAttribute("message", "wrong code");
            return new ModelAndView("redirect:/restorePassword/" + username);
        }
    }

    @GetMapping("/changePassword/{username}")
    public String changePassword(@PathVariable String username) {
        return "changingPassword";
    }

    @Transactional
    @PostMapping("/changePassword/{username}")
    public ModelAndView changePassword(
            @PathVariable String username,
            @RequestParam String password,
            @RequestParam String passwordChecker,
            Model model
    ) {
        if(passwordChecker.equals(password)){
            User user = userServiceImplementation.loadUserByUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            return new ModelAndView("redirect:/login");
        }
        //model.addAttribute("message", "passwords differ from each other");
        return new ModelAndView("redirect:/restorePassword/changePassword/" + username);
    }
}

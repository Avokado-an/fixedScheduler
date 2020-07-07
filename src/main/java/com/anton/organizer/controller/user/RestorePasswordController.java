package com.anton.organizer.controller.user;

import com.anton.organizer.dao.implementation.UserDaoImplementation;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.MailSenderService;
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
    private UserDaoImplementation userDaoImplementation;

    @Autowired
    public void setUserDaoImplementation(UserDaoImplementation userDaoImplementation) {
        this.userDaoImplementation = userDaoImplementation;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    MailSenderService mailSenderService = new MailSenderService();

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
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
        User user = userDaoImplementation.loadUserByUsername(username);
        String code = UUID.randomUUID().toString().substring(0, 4);
        mailSenderService.send(user.getEmail(), "Restore password", "Your code to change password - " + code);
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
        User user = userDaoImplementation.loadUserByUsername(username);
        if (activationCode.equals(user.getActivationCode()))
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
        if (passwordChecker.equals(password)) {
            User user = userDaoImplementation.loadUserByUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            return new ModelAndView("redirect:/login");
        }
        //todo model.addAttribute("message", "passwords differ from each other");
        return new ModelAndView("redirect:/restorePassword/changePassword/" + username);
    }
}

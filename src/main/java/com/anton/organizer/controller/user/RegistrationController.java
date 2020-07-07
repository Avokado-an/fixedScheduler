package com.anton.organizer.controller.user;

import com.anton.organizer.dao.implementation.UserDaoImplementation;
import com.anton.organizer.entity.Roles;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final String REGISTRATION = "registration";
    private UserDaoImplementation userDaoImplementation;

    @Autowired
    public void setUserDaoImplementation(UserDaoImplementation userDaoImplementation) {
        this.userDaoImplementation = userDaoImplementation;
    }

    private MailSenderService mailSenderService;

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String register() {
        return REGISTRATION;
    }

    @PostMapping
    public ModelAndView addUser(
            @RequestParam(name = "username") String name,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            Model model
    ) {
        try {
            User newUser = new User();
            newUser.setName(name);
            User dbUser = userDaoImplementation.loadUserByUsername(name);

            if (dbUser != null) {
                model.addAttribute("message", "This username is taken, try another");
                return new ModelAndView(REGISTRATION);
            }

            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setEmail(email);
            newUser.setActivationCode(UUID.randomUUID().toString().substring(0, 4));
            newUser.setActive(false);
            newUser.setRoles(Collections.singleton(Roles.USER));

            String message = "Hello, Your activation code - " + newUser.getActivationCode();
            mailSenderService.send(email, "Activation code", message);

            userDaoImplementation.save(newUser);
            return new ModelAndView("redirect:/activation/" + name);
        } catch (Exception ex) {
            model.addAttribute("message", "This mail is invalid, check it");
            return new ModelAndView(REGISTRATION);
        }
    }

    @ExceptionHandler(javax.mail.SendFailedException.class)
    public ModelAndView handleException(Exception ex) {
        return new ModelAndView("redirect:/tasks/registration");
    }
}

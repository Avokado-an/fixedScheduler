package com.anton.organizer.controller.user;

import com.anton.organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final String REGISTRATION = "registration";

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
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
        String urlRedirectResponse = service.addUser(model, name, password, email);
        return new ModelAndView(urlRedirectResponse);
    }

    @ExceptionHandler(javax.mail.SendFailedException.class)
    public ModelAndView handleException(Exception ex) {
        return new ModelAndView("redirect:/tasks/registration");
    }
}

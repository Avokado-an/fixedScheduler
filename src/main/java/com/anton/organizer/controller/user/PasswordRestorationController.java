package com.anton.organizer.controller.user;

import com.anton.organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/restorePassword")
public class PasswordRestorationController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
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
        service.sendPasswordRestorationCode(username);
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
        String redirectUrl = service.confirmRestorationCode(username, activationCode, model);
        return new ModelAndView(redirectUrl);
    }

    @GetMapping("/changePassword/{username}")
    public String changePassword(@PathVariable String username, Model model) {
        model.addAttribute("message", "Your new Password:");
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
        String redirectUrl = service.confirmPasswordChange(username, password, passwordChecker, model);
        return new ModelAndView(redirectUrl);
    }
}

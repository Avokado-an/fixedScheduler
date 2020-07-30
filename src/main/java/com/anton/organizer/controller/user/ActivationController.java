package com.anton.organizer.controller.user;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/activation/{username}")
public class ActivationController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
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
        String urlRedirect = service.activateUser(username, activationCode);
        return new ModelAndView(urlRedirect);
    }
}

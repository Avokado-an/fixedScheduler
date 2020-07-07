package com.anton.organizer.controller.user;

import com.anton.organizer.dao.implementation.UserDaoImplementation;
import com.anton.organizer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/activation/{username}")
public class ActivationController {
    private UserDaoImplementation userDaoImplementation;

    @Autowired
    public void setUserDaoImplementation(UserDaoImplementation userDaoImplementation) {
        this.userDaoImplementation = userDaoImplementation;
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
        User user = userDaoImplementation.loadUserByUsername(username);
        if (activationCode.equals(user.getActivationCode())) {
            user.setActive(true);
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/activation/" + username);
    }
}

package com.anton.organizer.service;

import com.anton.organizer.dao.implementation.UserDaoImplementation;
import com.anton.organizer.entity.Roles;
import com.anton.organizer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.UUID;

@Component
public class UserService {
    private static final String REGISTRATION = "registration";
    UserDaoImplementation userDao;

    @Autowired
    public void setUserDao(UserDaoImplementation userDao) {
        this.userDao = userDao;
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

    public String activateUser(String username, String activationCode) {
        User user = userDao.loadUserByUsername(username);
        String urlRedirectResponse;
        if (activationCode.equals(user.getActivationCode())) {
            user.setActive(true);
            urlRedirectResponse = "redirect:/login";
        } else {
            urlRedirectResponse = "redirect:/activation/" + username;
        }
        return urlRedirectResponse;
    }

    public String addUser(Model model, String name, String password, String email) {
        String urlRedirectResponse;
        try {
            User newUser = new User();
            newUser.setName(name);
            User dbUser = userDao.loadUserByUsername(name);

            if (dbUser == null) {
                newUser.setPassword(passwordEncoder.encode(password));
                newUser.setEmail(email);
                newUser.setActivationCode(UUID.randomUUID().toString().substring(0, 4));
                newUser.setActive(false);
                newUser.setRoles(Collections.singleton(Roles.USER));

                String message = "Hello, Your activation code - " + newUser.getActivationCode();
                mailSenderService.send(email, "Activation code", message);

                userDao.save(newUser);
                urlRedirectResponse = "redirect:/activation/" + name;
            } else {
                model.addAttribute("message", "This username is taken, try another");
                urlRedirectResponse = REGISTRATION;
            }
        } catch (Exception ex) {
            model.addAttribute("message", "This mail is invalid, check it");
            urlRedirectResponse = REGISTRATION;
        }
        return urlRedirectResponse;
    }

    private void setUserParameters() {

    }
}

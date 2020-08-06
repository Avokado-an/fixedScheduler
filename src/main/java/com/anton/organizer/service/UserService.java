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
    private static final String MESSAGE = "message";
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
                model.addAttribute(MESSAGE, "This username is taken, try another");
                urlRedirectResponse = REGISTRATION;
            }
        } catch (Exception ex) {
            model.addAttribute(MESSAGE, "This mail is invalid, check it");
            urlRedirectResponse = REGISTRATION;
        }
        return urlRedirectResponse;
    }

    public void sendPasswordRestorationCode(String username) {
        User user = userDao.loadUserByUsername(username);
        String code = UUID.randomUUID().toString().substring(0, 4);
        mailSenderService.send(user.getEmail(), "Restore password", "Your code to change password - " + code);
        user.setActivationCode(code);
    }

    public String confirmRestorationCode(String username, String restorationCode, Model model) {
        User user = userDao.loadUserByUsername(username);
        String responseUrl;
        if (restorationCode.equals(user.getActivationCode()))
            responseUrl = "redirect:/restorePassword/changePassword/" + username;
        else {
            model.addAttribute(MESSAGE, "wrong code");
            responseUrl = "redirect:/restorePassword/" + username;
        }
        return responseUrl;
    }

    public String confirmPasswordChange(String username, String newPassword, String newPasswordCopy, Model model) {
        String responseUrl;
        if (newPassword.equals(newPasswordCopy)) {
            User user = userDao.loadUserByUsername(username);
            user.setPassword(passwordEncoder.encode(newPassword));
            responseUrl = "redirect:/login";
        } else {
            responseUrl = "redirect:/restorePassword/changePassword/" + username;
        }
        model.addAttribute(MESSAGE, "passwords differ from each other");
        return responseUrl;
    }
}

package com.anton.organizer.controller.theme;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ThemeAdderController {
    private ThemesAndPlansService themesAndPlansService;

    @Autowired
    public void setThemesAndPlansService(ThemesAndPlansService themesAndPlansService) {
        this.themesAndPlansService = themesAndPlansService;
    }

    private ThemeDaoImplementation themeServiceImplementation;

    @Autowired
    public void setThemeDaoImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeServiceImplementation = themeDaoImplementation;
    }

    @GetMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, null);
        return "addTheme";
    }

    @PostMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model
    ) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        themeServiceImplementation.save(theme);
        themesAndPlansService.findPlansAndThemes(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        return "addTheme";
    }

    @PostMapping("/tasks/{themeId}/addTheme")
    public ModelAndView addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId
    ) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        Theme parentTheme = themeServiceImplementation.getById(Integer.parseInt(themeId));
        theme.setParentTheme(parentTheme);
        themeServiceImplementation.save(theme);
        themesAndPlansService.findPlansAndThemes(user, model, parentTheme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

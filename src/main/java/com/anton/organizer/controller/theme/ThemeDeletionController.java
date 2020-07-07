package com.anton.organizer.controller.theme;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/tasks")
public class ThemeDeletionController {
    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeDaoImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    private ThemesAndPlansService themesAndPlansService;

    @Autowired
    public void setThemesAndPlansService(ThemesAndPlansService themesAndPlansService) {
        this.themesAndPlansService = themesAndPlansService;
    }

    @Transactional
    @PostMapping("/deleteTheme/{themeId}")
    public String deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model
    ) {
        themeDaoImplementation.deleteThemeById(Integer.parseInt(themeId));
        user.deleteThemeById(Integer.parseInt(themeId));

        themesAndPlansService.findPlansAndThemes(user, model, null);

        return "redirect:/tasks";
    }

    @Transactional
    @PostMapping("/{parentThemeId}/deleteTheme/{themeId}")
    public ModelAndView deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model,
            @PathVariable String parentThemeId
    ) {
        themeDaoImplementation.deleteThemeById(Integer.parseInt(themeId));
        themesAndPlansService.findPlansAndThemes(user, model, themeDaoImplementation.getById(Integer.parseInt(parentThemeId)));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }
}

package com.anton.organizer.controller.theme;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansGetter;
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
    private ThemeDaoImplementation themeServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeDaoImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    @Transactional
    @PostMapping("/deleteTheme/{themeId}")
    public String deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model
    ) {
        themeServiceImplementation.deleteThemeById(Integer.parseInt(themeId));
        user.deleteThemeById(Integer.parseInt(themeId));

        themesAndPlansGetter.getPlansAndThemes(user, model, null);

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
        themeServiceImplementation.deleteThemeById(Integer.parseInt(themeId));
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(parentThemeId)));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }
}

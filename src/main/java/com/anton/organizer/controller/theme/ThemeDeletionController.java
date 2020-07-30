package com.anton.organizer.controller.theme;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import com.anton.organizer.service.ThemesService;
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
    private ThemesAndPlansModelingService themesAndPlansModelingService;

    @Autowired
    public void setThemesAndPlansModelingService(ThemesAndPlansModelingService themesAndPlansModelingService) {
        this.themesAndPlansModelingService = themesAndPlansModelingService;
    }

    private ThemesService themesService;

    @Autowired
    public void setThemesService(ThemesService themesService) {
        this.themesService = themesService;
    }

    @Transactional
    @PostMapping("/deleteTheme/{themeId}")
    public String deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model
    ) {
        themesService.deleteTheme(themeId, user);

        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);

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
        themesService.deleteTheme(themeId);
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, themesService.findTheme(themeId));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }
}

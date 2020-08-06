package com.anton.organizer.controller.theme;

import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import com.anton.organizer.service.ThemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ThemeAdditionController {
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

    @GetMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        return "addTheme";
    }

    @PostMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model
    ) {
        themesService.addTheme(user, description);

        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, themesService.findTheme(themeId));
        return "addTheme";
    }

    @PostMapping("/tasks/{themeId}/addTheme")
    public ModelAndView addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId
    ) {
        Theme parentTheme = themesService.findTheme(themeId);
        themesService.addTheme(user, description, parentTheme);

        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, parentTheme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

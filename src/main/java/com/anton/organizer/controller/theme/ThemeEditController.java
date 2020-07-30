package com.anton.organizer.controller.theme;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import com.anton.organizer.service.ThemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/tasks")
public class ThemeEditController {
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

    @GetMapping("/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        model.addAttribute("theme", themesService.findTheme(themeId));
        return "editTheme";
    }

    @Transactional
    @PostMapping("/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId
    ) {
        themesService.editTheme(description, themeId, user);
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/{parentThemeId}/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String parentThemeId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, themesService.findTheme(parentThemeId));
        model.addAttribute("theme", themesService.findTheme(themeId));
        return "editTheme";
    }

    @Transactional
    @PostMapping("/{parentThemeId}/editTheme/{themeId}")
    public ModelAndView editTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId,
            @PathVariable String parentThemeId
    ) {
        themesService.editTheme(description, themeId);
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, themesService.findTheme(parentThemeId));
        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }
}

package com.anton.organizer.controller;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import com.anton.organizer.service.ThemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TasksListController {
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

    @GetMapping()
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        return "tasks";
    }

    @GetMapping("/{themeId}")
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, themesService.findTheme(themeId));
        return "tasks";
    }
}

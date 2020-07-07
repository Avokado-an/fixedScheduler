package com.anton.organizer.controller;

import com.anton.organizer.dao.implementation.planDaoImplementation;
import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansService;
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
    planDaoImplementation planDaoImplementation;

    @Autowired
    public void setPlanDaoImplementation(planDaoImplementation planDaoImplementation) {
        this.planDaoImplementation = planDaoImplementation;
    }

    private ThemesAndPlansService themesAndPlansService;

    @Autowired
    public void setThemesAndPlansService(ThemesAndPlansService themesAndPlansService) {
        this.themesAndPlansService = themesAndPlansService;
    }

    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeDaoImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    @GetMapping()
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, null);
        return "tasks";
    }

    @GetMapping("/{themeId}")
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, themeDaoImplementation.getById(Integer.parseInt(themeId)));
        return "tasks";
    }
}

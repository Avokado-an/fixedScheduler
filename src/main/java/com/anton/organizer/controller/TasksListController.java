package com.anton.organizer.controller;

import com.anton.organizer.service.ThemesAndPlansGetter;
import com.anton.organizer.entity.User;
import com.anton.organizer.dao.implementation.PlanDaoImplementation;
import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TasksListController {

    PlanDaoImplementation planServiceImplementation;

    @Autowired
    public void setPlanServiceImplementation(PlanDaoImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    private ThemeDaoImplementation themeServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeDaoImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    @GetMapping()
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        return "tasks";
    }

    @GetMapping("/{themeId}")
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        return "tasks";
    }
}

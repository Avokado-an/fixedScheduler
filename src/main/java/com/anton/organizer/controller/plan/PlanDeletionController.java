package com.anton.organizer.controller.plan;

import com.anton.organizer.dao.implementation.planDaoImplementation;
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
public class PlanDeletionController {
    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeDaoImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    private planDaoImplementation planDaoImplementation;

    @Autowired
    public void setPlanDaoImplementation(planDaoImplementation planDaoImplementation) {
        this.planDaoImplementation = planDaoImplementation;
    }

    private ThemesAndPlansService themesAndPlansService;

    @Autowired
    public void setThemesAndPlansService(ThemesAndPlansService themesAndPlansService) {
        this.themesAndPlansService = themesAndPlansService;
    }

    @Transactional
    @PostMapping("/deletePlan/{planId}")
    public String deletePlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        int id = Integer.parseInt(planId);
        planDaoImplementation.deletePlan(id);
        user.deletePlanById(id);

        themesAndPlansService.findPlansAndThemes(user, model, null);

        return "redirect:/tasks";
    }

    @Transactional
    @PostMapping("/{themeId}/deletePlan/{planId}")
    public ModelAndView deletePlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String planId
    ) {
        planDaoImplementation.deletePlan(Integer.parseInt(planId));

        themesAndPlansService.findPlansAndThemes(user, model, themeDaoImplementation.getById(Integer.parseInt(themeId)));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

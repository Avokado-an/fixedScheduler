package com.anton.organizer.controller.plan;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.PlansService;
import com.anton.organizer.service.ThemesAndPlansModelingService;
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
    private ThemesAndPlansModelingService themesAndPlansModelingService;

    @Autowired
    public void setThemesAndPlansModelingService(ThemesAndPlansModelingService themesAndPlansModelingService) {
        this.themesAndPlansModelingService = themesAndPlansModelingService;
    }

    private PlansService plansService;

    @Autowired
    public void setPlansService(PlansService plansService) {
        this.plansService = plansService;
    }

    @Transactional
    @PostMapping("/deletePlan/{planId}")
    public String deletePlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        plansService.deletePlan(planId, user);
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);

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
        plansService.deletePlan(planId);
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, plansService.findTheme(themeId));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

package com.anton.organizer.controller.plan;

import com.anton.organizer.service.ThemesAndPlansGetter;
import com.anton.organizer.entity.User;
import com.anton.organizer.dao.implementation.PlanDaoImplementation;
import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
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
    private ThemeDaoImplementation themeServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeDaoImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    private PlanDaoImplementation planServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(PlanDaoImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    @Transactional
    @PostMapping("/deletePlan/{planId}")
    public String deletePlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        int id = Integer.parseInt(planId);
        planServiceImplementation.deletePlan(id);
        user.deletePlanById(id);

        themesAndPlansGetter.getPlansAndThemes(user, model, null);

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
        planServiceImplementation.deletePlan(Integer.parseInt(planId));

        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

package com.anton.organizer.controller.plan;

import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.PlansService;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
@RequestMapping
public class PlanAdderController {
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

    @GetMapping("/tasks/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansModelingService.inputModelMinDate(user, model, null);
        return "addPlan";
    }

    @PostMapping("/tasks/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model
    ) throws ParseException {
        plansService.addPlanComponent(user, description, dateString, timeString, null);

        themesAndPlansModelingService.inputModelMinDate(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansModelingService.inputModelMinDate(user, model, plansService.findTheme(themeId));
        return "addPlan";
    }

    @PostMapping("/tasks/{themeId}/addPlan")
    public ModelAndView addPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String themeId
    ) throws ParseException {
        Theme theme = plansService.findTheme(themeId);
        plansService.addPlanComponent(user, description, dateString, timeString, theme);

        themesAndPlansModelingService.inputModelMinDate(user, model, theme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}
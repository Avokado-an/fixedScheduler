package com.anton.organizer.controller.plan;

import com.anton.organizer.entity.User;
import com.anton.organizer.service.PlansService;
import com.anton.organizer.service.ThemesAndPlansModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/tasks")
public class PlanEditController {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    private static final String MIN_DATE = "minDate";
    private static final String PLAN = "plan";

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

    @GetMapping("/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        model.addAttribute(PLAN, plansService.findPlan(planId));
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));
        return "editPlan";
    }

    @Transactional
    @PostMapping("/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String planId
    ) throws ParseException {
        plansService.editPlan(description, timeString, dateString, planId);
        user.editPlan(Integer.parseInt(planId), plansService.findPlan(planId));

        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, null);
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));

        return "redirect:/tasks";
    }

    @GetMapping("/{themeId}/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String planId
    ) {
        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, plansService.findTheme(themeId));

        model.addAttribute(PLAN, plansService.findPlan(planId));
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));
        return "editPlan";
    }

    @Transactional
    @PostMapping("/{themeId}/editPlan/{planId}")
    public ModelAndView editPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String themeId,
            @PathVariable String planId
    ) throws ParseException {
        plansService.editPlan(description, timeString, dateString, planId);

        themesAndPlansModelingService.inputModelPlansAndThemes(user, model, plansService.findTheme(themeId));
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

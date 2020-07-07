package com.anton.organizer.controller.plan;

import com.anton.organizer.dao.implementation.planDaoImplementation;
import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping
public class PlanAdderController {
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

    private planDaoImplementation planDaoImplementation;

    @Autowired
    public void setPlanServiceImplementation(planDaoImplementation planDaoImplementation) {
        this.planDaoImplementation = planDaoImplementation;
    }

    @GetMapping("/tasks/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        inputModelData(user, model, null);
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
        addPlanComponent(user, description, dateString, timeString, null);

        inputModelData(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        inputModelData(user, model, themeDaoImplementation.getById(Integer.parseInt(themeId)));
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
        Theme theme = themeDaoImplementation.getById(Integer.parseInt(themeId));
        addPlanComponent(user, description, dateString, timeString, theme);

        inputModelData(user, model, theme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }


    private Plan createPlan(User user, String description, String timeString, String dateString) throws ParseException {
        Date time = new SimpleDateFormat("kk:mm").parse(timeString);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        return new Plan(description, time, date, user);
    }

    private void addPlanComponent(
            User user, String description, String dateString, String timeString, Theme theme) throws ParseException {
        Plan plan = createPlan(user, description, timeString, dateString);

        if (theme != null)
            theme.addPlan(plan);
        plan.setTheme(theme);
        user.addPlan(plan);

        planDaoImplementation.save(plan);
    }

    private void inputModelData(User user, Model model, Theme theme) {
        themesAndPlansService.findPlansAndThemes(user, model, theme);
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
    }
}
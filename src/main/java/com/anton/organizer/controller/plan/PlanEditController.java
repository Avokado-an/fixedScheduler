package com.anton.organizer.controller.plan;

import com.anton.organizer.dao.implementation.PlanDaoImplementation;
import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/tasks")
public class PlanEditController {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    private static final String MIN_DATE = "minDate";
    private static final String PLAN = "plan";

    private PlanDaoImplementation planServiceImplementation;

    @Autowired
    public void setServiceImplementation(PlanDaoImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    private ThemeDaoImplementation themeServiceImplementation;

    @Autowired
    public void setPlanServiceImplementation(ThemeDaoImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    @GetMapping("/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        model.addAttribute(PLAN, findPlan(planId));
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
        editPlan(description, timeString, dateString, planId);
        user.editPlan(Integer.parseInt(planId), findPlan(planId));

        themesAndPlansGetter.getPlansAndThemes(user, model, null);
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
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        model.addAttribute(PLAN, findPlan(planId));
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
        editPlan(description, timeString, dateString, planId);

        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }

    private void editPlan(String description, String timeString, String dateString, String planId) throws ParseException {
        Plan plan = findPlan(planId);
        plan.setDescription(description);
        plan.setTime(new SimpleDateFormat(TIME_FORMAT).parse(timeString));
        plan.setDate(new SimpleDateFormat(DATE_FORMAT).parse(dateString));

        planServiceImplementation.save(plan);
    }

    private Plan findPlan(String id) {
        return planServiceImplementation.getById(Integer.parseInt(id));
    }
}

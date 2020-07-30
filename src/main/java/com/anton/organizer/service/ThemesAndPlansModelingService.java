package com.anton.organizer.service;

import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThemesAndPlansModelingService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String MIN_DATE = "minDate";

    public void inputModelPlansAndThemes(User user, Model model, Theme theme) {
        List<Plan> plans;
        List<Theme> themes;

        if (theme == null) {
            plans = new ArrayList<>(findStartingPlans(user.getPlans()));
            themes = new ArrayList<>(findStartingThemes(user.getThemes()));
        } else {
            plans = new ArrayList<>(theme.getPlans());
            themes = new ArrayList<>(theme.getChildThemes());
        }

        model.addAttribute("themes", themes);
        model.addAttribute("plans", plans);
        model.addAttribute("user", user);
        model.addAttribute("currentTheme", theme);
    }

    public void inputModelMinDate(User user, Model model, Theme theme) {
        inputModelPlansAndThemes(user, model, theme);
        model.addAttribute(MIN_DATE, DateTimeFormatter.ofPattern(DATE_FORMAT).format(LocalDateTime.now()));
    }

    private List<Plan> findStartingPlans(List<Plan> plans) {
        List<Plan> startingPlans = new ArrayList<>();
        for (Plan plan : plans) {
            if (plan.getTheme() == null)
                startingPlans.add(plan);
        }
        return startingPlans;
    }

    private List<Theme> findStartingThemes(List<Theme> themes) {
        List<Theme> startingThemes = new ArrayList<>();
        for (Theme theme : themes) {
            if (theme.getParentTheme() == null)
                startingThemes.add(theme);
        }
        return startingThemes;
    }
}

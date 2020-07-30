package com.anton.organizer.service;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.dao.implementation.planDaoImplementation;
import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PlansService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";

    private planDaoImplementation planDaoImplementation;

    @Autowired
    public void setPlanServiceImplementation(planDaoImplementation planDaoImplementation) {
        this.planDaoImplementation = planDaoImplementation;
    }

    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    public Plan createPlan(User user, String description, String timeString, String dateString) throws ParseException {
        Date time = new SimpleDateFormat(TIME_FORMAT).parse(timeString);
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);

        return new Plan(description, time, date, user);
    }

    public void addPlanComponent(
            User user, String description, String dateString, String timeString, Theme theme) throws ParseException {
        Plan plan = createPlan(user, description, timeString, dateString);

        if (theme != null) {
            theme.addPlan(plan);
        }
        plan.setTheme(theme);
        user.addPlan(plan);

        planDaoImplementation.save(plan);
    }

    public void editPlan(String description, String timeString, String dateString, String planId) throws ParseException {
        Plan plan = findPlan(planId);
        plan.setDescription(description);
        plan.setTime(new SimpleDateFormat(TIME_FORMAT).parse(timeString));
        plan.setDate(new SimpleDateFormat(DATE_FORMAT).parse(dateString));

        planDaoImplementation.save(plan);
    }

    public Plan findPlan(String id) {
        return planDaoImplementation.getById(Integer.parseInt(id));
    }

    public Theme findTheme(String id) {
        return themeDaoImplementation.getById(Integer.parseInt(id));
    }

    public void deletePlan(String planId, User user) {
        int id = Integer.parseInt(planId);
        planDaoImplementation.deletePlan(id);
        user.deletePlanById(id);
    }

    public void deletePlan(String planId) {
        int id = Integer.parseInt(planId);
        planDaoImplementation.deletePlan(id);
    }
}

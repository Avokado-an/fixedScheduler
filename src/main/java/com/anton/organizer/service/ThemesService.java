package com.anton.organizer.service;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThemesService {
    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    public void addTheme(User user, String description) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        themeDaoImplementation.save(theme);
    }

    public void addTheme(User user, String description, Theme parentTheme) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        theme.setParentTheme(parentTheme);
        themeDaoImplementation.save(theme);
    }

    public void editTheme(String description, String themeId) {
        Theme theme = findTheme(themeId);
        theme.setName(description);

        themeDaoImplementation.save(theme);
    }

    public void editTheme(String description, String themeId, User user) {
        Theme theme = findTheme(themeId);
        theme.setName(description);
        user.editTheme(Integer.parseInt(themeId), findTheme(themeId));

        themeDaoImplementation.save(theme);
    }

    public void deleteTheme(String themeId, User user) {
        themeDaoImplementation.deleteThemeById(Integer.parseInt(themeId));
        user.deleteThemeById(Integer.parseInt(themeId));
    }

    public void deleteTheme(String themeId) {
        themeDaoImplementation.deleteThemeById(Integer.parseInt(themeId));
    }

    public Theme findTheme(String id) {
        return themeDaoImplementation.getById(Integer.parseInt(id));
    }
}

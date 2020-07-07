package com.anton.organizer.dao;

import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;

import java.util.List;

public interface ThemeDao {
    void save(Theme theme);

    List<Theme> getByOwner(User owner);

    Theme getById(int id);

    void deleteThemeById(int id);
}

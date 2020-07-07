package com.anton.organizer.dao.implementation;

import com.anton.organizer.dao.ThemeDao;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThemeDaoImplementation implements ThemeDao {
    ThemeRepository repository;

    @Autowired
    void setRepository(ThemeRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(Theme theme) {
        repository.save(theme);
    }

    @Override
    public List<Theme> getByOwner(User owner) {
        return repository.getByUser(owner);
    }

    @Override
    public Theme getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void deleteThemeById(int id) {
        repository.deleteById(id);
    }
}

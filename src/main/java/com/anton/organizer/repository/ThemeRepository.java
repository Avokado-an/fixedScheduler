package com.anton.organizer.repository;

import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> getByUser(User owner);

    Theme getById(int id);

    void deleteById(int id);
}

package com.anton.organizer.dao;

import com.anton.organizer.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDao extends UserDetailsService {
    void save(User user);

    User getById(Long id);

    User getByEmail(String email);
}

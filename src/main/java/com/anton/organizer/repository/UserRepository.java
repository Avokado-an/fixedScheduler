package com.anton.organizer.repository;

import com.anton.organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);
    User getById(Long id);
    User getByEmail(String email);
}

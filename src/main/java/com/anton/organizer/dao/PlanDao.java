package com.anton.organizer.dao;

import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.User;

import java.util.List;

public interface PlanDao {
    void save(Plan plan);

    List<Plan> findByOwner(User owner);

    Plan getById(int id);

    void deletePlan(int id);
}

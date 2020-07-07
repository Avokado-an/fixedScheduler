package com.anton.organizer.repository;

import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> getByOwner(User owner);

    Plan getById(int id);

    void deletePlanById(int id);
}

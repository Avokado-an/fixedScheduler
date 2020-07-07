package com.anton.organizer.dao.implementation;

import com.anton.organizer.entity.Plan;
import com.anton.organizer.entity.User;
import com.anton.organizer.repository.PlanRepository;
import com.anton.organizer.dao.PlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlanDaoImplementation implements PlanDao {
    private PlanRepository repository;

    @Autowired
    void setRepository(PlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Plan plan) {
        repository.save(plan);
    }

    @Override
    public List<Plan> findByOwner(User owner) {
        return repository.getByOwner(owner);
    }

    @Override
    public Plan getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void deletePlan(int id) {
        repository.deletePlanById(id);
    }
}

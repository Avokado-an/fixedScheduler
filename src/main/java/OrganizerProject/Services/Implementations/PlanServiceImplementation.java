package OrganizerProject.Services.Implementations;

import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.User;
import OrganizerProject.Repos.PlanRepository;
import OrganizerProject.Services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImplementation implements PlanService {
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

package OrganizerProject.Services;

import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.User;

import java.util.List;

public interface PlanService {
    void save(Plan plan);
    List<Plan> findByOwner(User owner);
    Plan getById(int id);
    void deletePlan(int id);
}

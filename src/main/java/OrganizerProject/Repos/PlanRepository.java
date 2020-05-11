package OrganizerProject.Repos;

import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getByOwner(User owner);

    Plan getById(int id);

    @Transactional
    void deletePlanById(int id);
}

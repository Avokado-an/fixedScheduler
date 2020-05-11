package OrganizerProject.Services;

import OrganizerProject.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(User user);
    User getById(Long id);
    User getByEmail(String email);
}

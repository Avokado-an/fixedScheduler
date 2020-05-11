package OrganizerProject.Services.Implementations;

import OrganizerProject.Entities.User;
import OrganizerProject.Repos.UserRepository;
import OrganizerProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.getByName(s);
    }


}

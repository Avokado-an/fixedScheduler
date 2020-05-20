package OrganizerProject.Services.Implementations;

import OrganizerProject.Entities.Theme;
import OrganizerProject.Entities.User;
import OrganizerProject.Repos.ThemeRepository;
import OrganizerProject.Services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ThemeServiceImplementation implements ThemeService {
    ThemeRepository repository;

    @Autowired
    void setRepository(ThemeRepository repository) {
           this.repository = repository;
    }


    @Override
    public void save(Theme theme) {
        repository.save(theme);
    }

    @Override
    public List<Theme> getByOwner(User owner) {
        return repository.getByUser(owner);
    }

    @Override
    public Theme getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void deleteThemeById(int id) {
        repository.deleteById(id);
    }
}

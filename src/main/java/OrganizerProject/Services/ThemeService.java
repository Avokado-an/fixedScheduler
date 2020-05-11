package OrganizerProject.Services;

import OrganizerProject.Entities.Theme;
import OrganizerProject.Entities.User;

import java.util.List;

public interface ThemeService {
    void save(Theme theme);
    List<Theme> getByOwner(User owner);
    Theme getById(int id);
    void deleteThemeById(int id);
}

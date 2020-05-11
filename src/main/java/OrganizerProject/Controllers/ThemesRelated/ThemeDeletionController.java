package OrganizerProject.Controllers.ThemesRelated;

import OrganizerProject.ControllerTools.ThemesAndPlansGetter;
import OrganizerProject.Entities.Theme;
import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.ThemeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/tasks")
public class ThemeDeletionController {
    private ThemeServiceImplementation themeServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeServiceImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    @Transactional
    @PostMapping("/deleteTheme/{themeId}")
    public String deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model
    ) {
        themeServiceImplementation.deleteThemeById(Integer.parseInt(themeId));
        user.deleteThemeById(Integer.parseInt(themeId));

        themesAndPlansGetter.getPlansAndThemes(user, model, null);

        return "redirect:/tasks";
    }

    @Transactional
    @PostMapping("/{parentThemeId}/deleteTheme/{themeId}")
    public ModelAndView deleteTheme(
            @AuthenticationPrincipal User user,
            @PathVariable String themeId,
            Model model,
            @PathVariable String parentThemeId
    ) {
        themeServiceImplementation.deleteThemeById(Integer.parseInt(themeId));
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(parentThemeId)));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }

    private Theme findTheme(String id) {
        return themeServiceImplementation.getById(Integer.parseInt(id));
    }
}

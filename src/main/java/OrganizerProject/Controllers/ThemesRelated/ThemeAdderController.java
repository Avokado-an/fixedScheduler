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

@Controller
@RequestMapping
public class ThemeAdderController {

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    private ThemeServiceImplementation themeServiceImplementation;

    @Autowired
    public void setThemeServiceImplementation(ThemeServiceImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    @GetMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        return "addTheme";
    }

    @PostMapping("/tasks/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model
    ) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        themeServiceImplementation.save(theme);
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addTheme")
    public String addTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        return "addTheme";
    }

    @PostMapping("/tasks/{themeId}/addTheme")
    public ModelAndView addTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId
    ) {
        Theme theme = new Theme(description, user);
        user.addPlanTheme(theme);
        Theme parentTheme = themeServiceImplementation.getById(Integer.parseInt(themeId));
        theme.setParentTheme(parentTheme);
        themeServiceImplementation.save(theme);
        themesAndPlansGetter.getPlansAndThemes(user, model, parentTheme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }
}

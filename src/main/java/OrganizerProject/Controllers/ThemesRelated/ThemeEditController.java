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
public class ThemeEditController {
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

    @GetMapping("/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        model.addAttribute("theme", findTheme(themeId));
        return "editTheme";
    }

    @Transactional
    @PostMapping("/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId
    ) {
        editTheme(description, themeId);
        user.editTheme(Integer.parseInt(themeId), findTheme(themeId));

        themesAndPlansGetter.getPlansAndThemes(user, model, null);

        return "redirect:/tasks";
    }

    @GetMapping("/{parentThemeId}/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String parentThemeId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(parentThemeId)));
        model.addAttribute("theme", findTheme(themeId));
        return "editTheme";
    }

    @Transactional
    @PostMapping("/{parentThemeId}/editTheme/{themeId}")
    public ModelAndView editTheme(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            Model model,
            @PathVariable String themeId,
            @PathVariable String parentThemeId
    ) {
        editTheme(description, themeId);

        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(parentThemeId)));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }

    private void editTheme(String description, String themeId) {
        Theme theme = findTheme(themeId);
        theme.setName(description);

        themeServiceImplementation.save(theme);
    }

    private Theme findTheme(String id) {
        return themeServiceImplementation.getById(Integer.parseInt(id));
    }
}

package OrganizerProject.Controllers;

import OrganizerProject.ControllerTools.ThemesAndPlansGetter;
import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.PlanServiceImplementation;
import OrganizerProject.Services.Implementations.ThemeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TasksListController {

    PlanServiceImplementation planServiceImplementation;

    @Autowired
    public void setPlanServiceImplementation(PlanServiceImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

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

    @GetMapping()
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        return "tasks";
    }

    @GetMapping("/{themeId}")
    public String showPlans(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        return "tasks";
    }
}

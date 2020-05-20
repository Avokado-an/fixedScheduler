package OrganizerProject.Controllers.PlansRelated;

import OrganizerProject.ControllerTools.ThemesAndPlansGetter;
import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.Theme;
import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.PlanServiceImplementation;
import OrganizerProject.Services.Implementations.ThemeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping
public class PlanAdderController {

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

    private PlanServiceImplementation planServiceImplementation;

    @Autowired
    public void setPlanServiceImplementation(PlanServiceImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

    @GetMapping("/tasks/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        inputModelData(user, model, null);
        return "addPlan";
    }

    @PostMapping("/tasks/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model
    ) throws ParseException {
        addPlanComponent(user, description, dateString, timeString, null);

        inputModelData(user, model, null);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{themeId}/addPlan")
    public String addPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        inputModelData(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        return "addPlan";
    }

    @PostMapping("/tasks/{themeId}/addPlan")
    public ModelAndView addPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String themeId
    ) throws ParseException {
        Theme theme = themeServiceImplementation.getById(Integer.parseInt(themeId));
        addPlanComponent(user, description, dateString, timeString, theme);

        inputModelData(user, model, theme);
        return new ModelAndView("redirect:/tasks/" + themeId);
    }



    private Plan createPlan(User user, String description, String timeString, String dateString) throws ParseException {
        Date time = new SimpleDateFormat("kk:mm").parse(timeString);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        return new Plan(description, time, date, user);
    }

    private void addPlanComponent(User user, String description, String dateString, String timeString, Theme theme) throws ParseException {
        Plan plan = createPlan(user, description, timeString, dateString);

        if (theme != null)
            theme.addPlan(plan);
        plan.setTheme(theme);
        user.addPlan(plan);

        planServiceImplementation.save(plan);
    }

    private void inputModelData(User user, Model model, Theme theme) {
        themesAndPlansGetter.getPlansAndThemes(user, model, theme);
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
    }
}
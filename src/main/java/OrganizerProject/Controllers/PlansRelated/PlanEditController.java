package OrganizerProject.Controllers.PlansRelated;

import OrganizerProject.ControllerTools.ThemesAndPlansGetter;
import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.User;
import OrganizerProject.Services.Implementations.PlanServiceImplementation;
import OrganizerProject.Services.Implementations.ThemeServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/tasks")
public class PlanEditController {

    private PlanServiceImplementation planServiceImplementation;

    @Autowired
    public void setServiceImplementation(PlanServiceImplementation planServiceImplementation) {
        this.planServiceImplementation = planServiceImplementation;
    }

    private ThemesAndPlansGetter themesAndPlansGetter;

    @Autowired
    public void setThemesAndPlansGetter(ThemesAndPlansGetter themesAndPlansGetter) {
        this.themesAndPlansGetter = themesAndPlansGetter;
    }

    private ThemeServiceImplementation themeServiceImplementation;

    @Autowired
    public void setPlanServiceImplementation(ThemeServiceImplementation themeServiceImplementation) {
        this.themeServiceImplementation = themeServiceImplementation;
    }

    @GetMapping("/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String planId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        model.addAttribute("plan", findPlan(planId));
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
        return "editPlan";
    }

    @Transactional
    @PostMapping("/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String planId
    ) throws ParseException {
        editPlan(description, timeString, dateString, planId);
        user.editPlan(Integer.parseInt(planId), findPlan(planId));

        themesAndPlansGetter.getPlansAndThemes(user, model, null);
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));

        return "redirect:/tasks";
    }

    @GetMapping("/{themeId}/editPlan/{planId}")
    public String editPlan(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String planId
    ) {
        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        model.addAttribute("plan", findPlan(planId));
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
        return "editPlan";
    }

    @Transactional
    @PostMapping("/{themeId}/editPlan/{planId}")
    public ModelAndView editPlan(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "deadlineDate") String dateString,
            @RequestParam(name = "deadlineTime") String timeString,
            Model model,
            @PathVariable String themeId,
            @PathVariable String planId
    ) throws ParseException {
        editPlan(description, timeString, dateString, planId);

        themesAndPlansGetter.getPlansAndThemes(user, model, themeServiceImplementation.getById(Integer.parseInt(themeId)));
        model.addAttribute("minDate", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));

        return new ModelAndView("redirect:/tasks/" + themeId);
    }

    private void editPlan(String description, String timeString, String dateString, String planId) throws ParseException {
        Plan plan = findPlan(planId);
        plan.setDescription(description);
        plan.setTime(new SimpleDateFormat("kk:mm").parse(timeString));
        plan.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));

        planServiceImplementation.save(plan);
    }

    private Plan findPlan(String id) {
        return planServiceImplementation.getById(Integer.parseInt(id));
    }
}

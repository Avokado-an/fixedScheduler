package OrganizerProject.ControllerTools;

import OrganizerProject.Entities.Plan;
import OrganizerProject.Entities.Theme;
import OrganizerProject.Entities.User;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ThemesAndPlansGetter {
    public void getPlansAndThemes(User user, Model model, Theme theme) {
        Set<Plan> plans;
        Set<Theme> themes;

        if(theme == null) {
            plans = new TreeSet<>(getStartingPlans(user.getPlans()));
            themes = new TreeSet<>(getStartingThemes(user.getThemes()));
        }
        else {
            plans = new TreeSet<>(theme.getPlans());
            themes = new TreeSet<>(theme.getChildThemes());
        }

        model.addAttribute("themes", themes);
        model.addAttribute("plans", plans);
        model.addAttribute("user", user);
        model.addAttribute("currentTheme", theme);
    }

    private List<Plan> getStartingPlans(List<Plan> plans) {
        List<Plan> startingPlans = new ArrayList<>();
        for(Plan plan: plans) {
            if(plan.getTheme() == null)
                startingPlans.add(plan);
        }
        return startingPlans;
    }

    private List<Theme> getStartingThemes(List<Theme> themes) {
        List<Theme> startingThemes = new ArrayList<>();
        for(Theme theme: themes) {
            if(theme.getParentTheme() == null)
                startingThemes.add(theme);
        }
        return startingThemes;
    }
}

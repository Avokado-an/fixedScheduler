package com.anton.organizer.controller.theme;

import com.anton.organizer.dao.implementation.ThemeDaoImplementation;
import com.anton.organizer.entity.Theme;
import com.anton.organizer.entity.User;
import com.anton.organizer.service.ThemesAndPlansService;
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
    private ThemeDaoImplementation themeDaoImplementation;

    @Autowired
    public void setThemeDaoImplementation(ThemeDaoImplementation themeDaoImplementation) {
        this.themeDaoImplementation = themeDaoImplementation;
    }

    private ThemesAndPlansService themesAndPlansService;

    @Autowired
    public void setThemesAndPlansService(ThemesAndPlansService themesAndPlansService) {
        this.themesAndPlansService = themesAndPlansService;
    }

    @GetMapping("/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, null);
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

        themesAndPlansService.findPlansAndThemes(user, model, null);

        return "redirect:/tasks";
    }

    @GetMapping("/{parentThemeId}/editTheme/{themeId}")
    public String editTheme(
            @AuthenticationPrincipal User user,
            Model model,
            @PathVariable String themeId,
            @PathVariable String parentThemeId
    ) {
        themesAndPlansService.findPlansAndThemes(user, model, themeDaoImplementation.getById(Integer.parseInt(parentThemeId)));
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

        themesAndPlansService.findPlansAndThemes(user, model, themeDaoImplementation.getById(Integer.parseInt(parentThemeId)));

        return new ModelAndView("redirect:/tasks/" + parentThemeId);
    }

    private void editTheme(String description, String themeId) {
        Theme theme = findTheme(themeId);
        theme.setName(description);

        themeDaoImplementation.save(theme);
    }

    private Theme findTheme(String id) {
        return themeDaoImplementation.getById(Integer.parseInt(id));
    }
}

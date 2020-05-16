package OrganizerProject.Configs;

import OrganizerProject.ControllerTools.ThemesAndPlansGetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Bean
    public ThemesAndPlansGetter getThemesAndPlansGetter() {
        return new ThemesAndPlansGetter();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}

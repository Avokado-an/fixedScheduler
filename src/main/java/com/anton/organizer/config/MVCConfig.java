package com.anton.organizer.config;

import com.anton.organizer.service.ThemesAndPlansService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    //@Bean
    //public ThemesAndPlansService getThemesAndPlansService() {
    //    return new ThemesAndPlansService();
   // }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}

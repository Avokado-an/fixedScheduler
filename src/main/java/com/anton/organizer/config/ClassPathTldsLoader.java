package com.anton.organizer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

public class ClassPathTldsLoader {
    private static final String SECURITY_TLD = "/META-INF/security.tld";

    private final List<String> classPathTlds;

    public ClassPathTldsLoader(String... classPathTlds) {
        super();
        if (classPathTlds.length == 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void loadClassPathTlds() {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }
}

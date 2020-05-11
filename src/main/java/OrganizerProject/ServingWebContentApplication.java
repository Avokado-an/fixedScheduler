package OrganizerProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static freemarker.template.utility.Collections12.singletonList;

@SpringBootApplication
public class ServingWebContentApplication {


    public ServingWebContentApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("classpath:/META-INF/security.tld"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}

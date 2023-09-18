package com.example.G4_DailyReport.config;

<<<<<<< HEAD
=======
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
>>>>>>> c91ea22949c0995572eaeeeee2065396af86422e
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/reports/new").setViewName("user/new_report");
    }
<<<<<<< HEAD
=======
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
>>>>>>> c91ea22949c0995572eaeeeee2065396af86422e
}

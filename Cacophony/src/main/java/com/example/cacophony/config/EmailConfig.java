package com.example.cacophony.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class EmailConfig {

    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/email/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(false);  // TODO Good for development, set to true in production
        return resolver;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(emailTemplateResolver()); // TODO Optional add Dialect, etc...
        return templateEngine;
    }
}

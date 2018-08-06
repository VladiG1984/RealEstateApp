package com.realestateapp.realestateapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.realestateapp.realestateapp.models.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public SessionFactory createSessionFactory() {
        System.out.println("SessionFactory was created.");

        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(Estate.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Material.class)
                .addAnnotatedClass(Type.class)
                .buildSessionFactory();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(new UrlTemplateResolver());
        return templateEngine;
    }
}
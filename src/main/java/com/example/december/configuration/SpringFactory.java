package com.example.december.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFactory {
    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();

        openAPI.setInfo(new Info());
        openAPI.getInfo().setTitle("Інструментальні засоби та технології: Backend-програмування");
        openAPI.getInfo().setDescription("Цей проект включає лабораторні роботи №1-3");
        return openAPI;
    }
}

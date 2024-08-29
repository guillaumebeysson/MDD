package com.openclassrooms.back.configuration;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Swagger link http://localhost:8080/swagger-ui/index.html

    /**
     * Configuration de l'API Swagger
     * @return La configuration de l'API Swagger
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MDD API")
                        .version("1.0")
                        .description("API documentation for MDD application")
                        .contact(new Contact()
                                .name("Guillaume Beysson")
                                .email("guillaume.beysson@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://docs.spring.io/spring-framework/reference/index.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("MDD Github Source Code")
                        .url("https://github.com/guillaumebeysson/MDD"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}

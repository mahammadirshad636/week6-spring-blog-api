package com.blogapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Management REST API")
                        .version("1.0.0")
                        .description("Comprehensive RESTful API for blog management with posts, categories, and comments")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@blogapi.com")
                                .url("https://blogapi.com"))
                );
    }
}

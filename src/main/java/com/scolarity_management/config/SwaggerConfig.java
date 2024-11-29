package com.scolarity_management.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

@SecuritySchemes({
        @SecurityScheme(
                type = SecuritySchemeType.HTTP,
                in = SecuritySchemeIn.HEADER,
                name = "Access Token Authorization",
                scheme = "Bearer",
                bearerFormat = "JWT"),
        @SecurityScheme(
                type = SecuritySchemeType.HTTP,
                in = SecuritySchemeIn.HEADER,
                name = "Refresh Token Authorization",
                scheme = "Bearer",
                bearerFormat = "JWT")
})
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Gestion Examens Backend")
                        .description("SpringBoot Application for Gestion Examens Backend")
                        .version("v1"));
    }
}

package com.chaeum.api.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "로컬 서버"),
                @Server(url = "https://api.example.com", description = "운영 서버")
        }
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .name("JWT Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("access token을 넣어주세요!"))) // JWT 토큰 추가 시 사용
                .info(apiInfo());
        // .addSecurityItem(new SecurityRequirement().addList("bearer-jwt")); // JWT 인증 적용
    }

    private Info apiInfo() {
        return new Info()
                .title("Chaeum REST API Swagger Test Page")
                .description("Developed by Team Chaeum")
                .contact(new Contact()
                        .name("Chaeum BE GitHub")
                        .url("https://github.com/2025-Advanced-Capstone-Chaeum/BE"))
                .version("1.0.0");
    }
}

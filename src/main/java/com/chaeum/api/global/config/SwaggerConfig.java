package com.chaeum.api.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("https://chaeum.site").description("Chaeum Server"));
        servers.add(new Server().url("http://localhost:8080").description("Local Server"));

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .name("JWT Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("access token을 넣어주세요!"))) // JWT 토큰 추가 시 사용
                .info(apiInfo())
                .servers(servers);
                // 시큐리티 환경 구축 시 추가
                // .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
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

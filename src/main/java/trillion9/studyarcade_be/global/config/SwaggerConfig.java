package trillion9.studyarcade_be.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import trillion9.studyarcade_be.global.jwt.JwtUtil;

@OpenAPIDefinition(
        info = @Info(title = "StudyHub API 명세서",
                description = "StudyHub API 명세서",
                version = "v3"),
        servers = {
                @Server(url = "https://studyhub.shop", description = "StudyHub Server"),
                @Server(url = "http://localhost:8080", description = "LocalHost Server")
        })

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info()
                .version("v3.0.0")
                .title("StudyHub")
                .description("Api Description");

        String access_token_header = JwtUtil.ACCESS_TOKEN;
        String refresh_token_header = JwtUtil.REFRESH_TOKEN;

        // 헤더에 security scheme 도 같이 보내게 만드는 것
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(access_token_header).addList(refresh_token_header);

        Components components = new Components()
                .addSecuritySchemes(access_token_header, new SecurityScheme()
                        .name(access_token_header)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER))
                .addSecuritySchemes(refresh_token_header, new SecurityScheme()
                        .name(refresh_token_header)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
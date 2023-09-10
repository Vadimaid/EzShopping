package kg.ezshopping.ezshopping.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("EzShopping");

        SecurityRequirement bearerTokenSecurityRequirement =
                new SecurityRequirement().addList("Bearer Authentication");

        Components jwtAuthenticationComponent =
                new Components().addSecuritySchemes("Bearer Authentication", this.createAPIKeyScheme());

        return new OpenAPI()
                .info(info)
                .addSecurityItem(bearerTokenSecurityRequirement)
                .components(jwtAuthenticationComponent);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}

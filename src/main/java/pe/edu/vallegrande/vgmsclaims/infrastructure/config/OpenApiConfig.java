package pe.edu.vallegrande.vgmsclaims.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:vg-ms-claims-incidents}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Claims & Incidents Microservice API")
                        .description("API para gestión de quejas e incidentes del sistema de agua potable")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Valle Grande")
                                .email("soporte@vallegrande.edu.pe")
                                .url("https://vallegrande.edu.pe"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://vallegrande.edu.pe/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.vallegrande.edu.pe")
                                .description("Production Server")
                ));
    }
}

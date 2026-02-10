package pe.edu.vallegrande.vgmsclaims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del microservicio de Quejas e Incidentes
 * 
 * Este microservicio gestiona:
 * - Quejas de clientes sobre el servicio de agua
 * - Incidentes técnicos de infraestructura
 * 
 * Arquitectura: Hexagonal (Ports & Adapters)
 * Framework: Spring Boot WebFlux (Reactivo)
 * Base de datos: MongoDB
 * Mensajería: RabbitMQ
 */
@SpringBootApplication
public class VgMsClaimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VgMsClaimsApplication.class, args);
    }
}

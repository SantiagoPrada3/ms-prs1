package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.out.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IUserServiceClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Cliente para comunicación con el microservicio de usuarios
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceClientImpl implements IUserServiceClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.user-service.url:http://localhost:8081}")
    private String userServiceUrl;

    @Override
    public Mono<Map<String, Object>> getUserById(String userId) {
        log.debug("Obteniendo usuario por ID: {}", userId);
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/api/users/{userId}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo usuario {}: {}", userId, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Map<String, Object>> getUserByEmail(String email) {
        log.debug("Obteniendo usuario por email: {}", email);
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/api/users/email/{email}", email)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo usuario por email {}: {}", email, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> existsUser(String userId) {
        log.debug("Verificando existencia de usuario: {}", userId);
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/api/users/{userId}/exists", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> {
                    log.warn("Error verificando usuario {}: {}", userId, e.getMessage());
                    return Mono.just(true);
                });
    }

    @Override
    public Mono<List<Map<String, Object>>> getUsersByOrganization(String organizationId) {
        log.debug("Obteniendo usuarios por organización: {}", organizationId);
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/api/users/organization/{organizationId}", organizationId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo usuarios de organización {}: {}", organizationId, e.getMessage());
                    return Mono.empty();
                });
    }
}

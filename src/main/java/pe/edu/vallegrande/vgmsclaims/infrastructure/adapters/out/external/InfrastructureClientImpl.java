package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.out.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IInfrastructureClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Cliente para comunicación con el microservicio de infraestructura
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InfrastructureClientImpl implements IInfrastructureClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.infrastructure-service.url:http://localhost:8082}")
    private String infrastructureServiceUrl;

    @Override
    public Mono<Map<String, Object>> getZoneById(String zoneId) {
        log.debug("Obteniendo zona por ID: {}", zoneId);
        return webClientBuilder.build()
                .get()
                .uri(infrastructureServiceUrl + "/api/zones/{zoneId}", zoneId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo zona {}: {}", zoneId, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Map<String, Object>> getWaterBoxById(String waterBoxId) {
        log.debug("Obteniendo caja de agua por ID: {}", waterBoxId);
        return webClientBuilder.build()
                .get()
                .uri(infrastructureServiceUrl + "/api/water-boxes/{waterBoxId}", waterBoxId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo caja de agua {}: {}", waterBoxId, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> existsZone(String zoneId) {
        log.debug("Verificando existencia de zona: {}", zoneId);
        return webClientBuilder.build()
                .get()
                .uri(infrastructureServiceUrl + "/api/zones/{zoneId}/exists", zoneId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> {
                    log.warn("Error verificando zona {}: {}", zoneId, e.getMessage());
                    return Mono.just(true);
                });
    }

    @Override
    public Mono<Boolean> existsWaterBox(String waterBoxId) {
        log.debug("Verificando existencia de caja de agua: {}", waterBoxId);
        return webClientBuilder.build()
                .get()
                .uri(infrastructureServiceUrl + "/api/water-boxes/{waterBoxId}/exists", waterBoxId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> {
                    log.warn("Error verificando caja de agua {}: {}", waterBoxId, e.getMessage());
                    return Mono.just(true);
                });
    }

    @Override
    public Mono<List<Map<String, Object>>> getWaterBoxesByZone(String zoneId) {
        log.debug("Obteniendo cajas de agua de zona: {}", zoneId);
        return webClientBuilder.build()
                .get()
                .uri(infrastructureServiceUrl + "/api/zones/{zoneId}/water-boxes", zoneId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .onErrorResume(e -> {
                    log.warn("Error obteniendo cajas de agua de zona {}: {}", zoneId, e.getMessage());
                    return Mono.empty();
                });
    }
}

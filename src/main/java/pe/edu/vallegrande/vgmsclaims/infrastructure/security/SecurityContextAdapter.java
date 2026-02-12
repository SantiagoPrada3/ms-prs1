package pe.edu.vallegrande.vgmsclaims.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.ISecurityContext;
import reactor.core.publisher.Mono;

/**
 * Adaptador que implementa el puerto de seguridad usando el contexto del gateway
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityContextAdapter implements ISecurityContext {

    @Override
    public Mono<AuthenticatedUser> getCurrentUser() {
        return GatewayHeadersFilter.getCurrentUser()
                .doOnNext(user -> log.debug("Usuario actual: {}", user.getUserId()));
    }

    @Override
    public Mono<String> getCurrentUserId() {
        return GatewayHeadersFilter.getCurrentUserId()
                .doOnNext(userId -> log.debug("ID de usuario actual: {}", userId));
    }

    @Override
    public Mono<String> getCurrentOrganizationId() {
        return GatewayHeadersFilter.getCurrentOrganizationId()
                .doOnNext(orgId -> log.debug("Organización actual: {}", orgId));
    }

    @Override
    public Mono<Boolean> hasRole(String role) {
        return GatewayHeadersFilter.getCurrentUser()
                .map(user -> user.getRoles() != null && user.getRoles().contains(role))
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> isAdmin() {
        return GatewayHeadersFilter.getCurrentUser()
                .map(AuthenticatedUser::isAdmin)
                .defaultIfEmpty(false);
    }
}

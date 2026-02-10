package pe.edu.vallegrande.vgmsclaims.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Extrae información del usuario autenticado desde los headers del gateway
 */
@Slf4j
@Component
public class GatewayHeadersExtractor {

    // Headers personalizados del gateway
    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USERNAME = "X-Username";
    public static final String HEADER_EMAIL = "X-User-Email";
    public static final String HEADER_ORGANIZATION_ID = "X-Organization-Id";
    public static final String HEADER_ROLES = "X-User-Roles";
    public static final String HEADER_PERMISSIONS = "X-User-Permissions";

    /**
     * Extrae el usuario autenticado desde los headers de la petición
     */
    public Optional<AuthenticatedUser> extractUser(ServerHttpRequest request) {
        String userId = getHeader(request, HEADER_USER_ID);
        
        if (userId == null || userId.isEmpty()) {
            log.debug("No se encontró header de usuario autenticado");
            return Optional.empty();
        }

        AuthenticatedUser user = AuthenticatedUser.builder()
                .userId(userId)
                .username(getHeader(request, HEADER_USERNAME))
                .email(getHeader(request, HEADER_EMAIL))
                .organizationId(getHeader(request, HEADER_ORGANIZATION_ID))
                .roles(parseCommaSeparated(getHeader(request, HEADER_ROLES)))
                .permissions(parseCommaSeparated(getHeader(request, HEADER_PERMISSIONS)))
                .build();

        log.debug("Usuario extraído de headers: {} (org: {})", user.getUserId(), user.getOrganizationId());
        return Optional.of(user);
    }

    /**
     * Extrae el ID del usuario desde los headers
     */
    public Optional<String> extractUserId(ServerHttpRequest request) {
        return Optional.ofNullable(getHeader(request, HEADER_USER_ID))
                .filter(s -> !s.isEmpty());
    }

    /**
     * Extrae el ID de la organización desde los headers
     */
    public Optional<String> extractOrganizationId(ServerHttpRequest request) {
        return Optional.ofNullable(getHeader(request, HEADER_ORGANIZATION_ID))
                .filter(s -> !s.isEmpty());
    }

    /**
     * Extrae los roles desde los headers
     */
    public Set<String> extractRoles(ServerHttpRequest request) {
        return parseCommaSeparated(getHeader(request, HEADER_ROLES));
    }

    private String getHeader(ServerHttpRequest request, String headerName) {
        return request.getHeaders().getFirst(headerName);
    }

    private Set<String> parseCommaSeparated(String value) {
        if (value == null || value.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }
}

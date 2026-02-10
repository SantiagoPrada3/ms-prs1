package pe.edu.vallegrande.vgmsclaims.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Representa la información del usuario autenticado extraída del gateway
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private String userId;
    private String username;
    private String email;
    private String organizationId;
    private Set<String> roles;
    private Set<String> permissions;

    /**
     * Verifica si el usuario tiene un rol específico
     */
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    /**
     * Verifica si el usuario tiene un permiso específico
     */
    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean isAdmin() {
        return hasRole("ADMIN") || hasRole("ROLE_ADMIN");
    }

    /**
     * Verifica si el usuario es técnico
     */
    public boolean isTechnician() {
        return hasRole("TECHNICIAN") || hasRole("ROLE_TECHNICIAN");
    }

    /**
     * Verifica si el usuario es cliente
     */
    public boolean isClient() {
        return hasRole("CLIENT") || hasRole("ROLE_CLIENT");
    }
}

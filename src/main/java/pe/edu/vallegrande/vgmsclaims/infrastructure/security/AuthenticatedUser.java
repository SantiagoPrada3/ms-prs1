package pe.edu.vallegrande.vgmsclaims.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa la información del usuario autenticado extraída del gateway
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private String userId;
    private String organizationId;
    private String email;
    private List<String> roles;

    /**
     * Verifica si el usuario es superadministrador
     */
    public boolean isSuperAdmin() {
        return roles != null && roles.contains("SUPER_ADMIN");
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean isAdmin() {
        return roles != null && (roles.contains("ADMIN") || roles.contains("SUPER_ADMIN"));
    }

    /**
     * Verifica si el usuario pertenece a una organización
     */
    public boolean belongsToOrganization(String orgId) {
        if (isSuperAdmin()) return true;
        return organizationId != null && organizationId.equals(orgId);
    }

    /**
     * Verifica si el usuario puede crear un rol específico
     */
    public boolean canCreateRole(String role) {
        if (isSuperAdmin()) return true;
        if (isAdmin()) {
            return !"SUPER_ADMIN".equals(role) && !"ADMIN".equals(role);
        }
        return false;
    }
}

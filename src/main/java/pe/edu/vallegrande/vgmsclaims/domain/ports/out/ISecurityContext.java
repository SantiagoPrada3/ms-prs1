package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.infrastructure.security.AuthenticatedUser;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el contexto de seguridad
 */
public interface ISecurityContext {
    
    /**
     * Obtiene el usuario autenticado actual
     * @return el usuario autenticado
     */
    Mono<AuthenticatedUser> getCurrentUser();
    
    /**
     * Obtiene el ID del usuario autenticado actual
     * @return el ID del usuario
     */
    Mono<String> getCurrentUserId();
    
    /**
     * Obtiene el ID de la organización del usuario actual
     * @return el ID de la organización
     */
    Mono<String> getCurrentOrganizationId();
    
    /**
     * Verifica si el usuario actual tiene un rol específico
     * @param role rol a verificar
     * @return true si tiene el rol
     */
    Mono<Boolean> hasRole(String role);
    
    /**
     * Verifica si el usuario actual es administrador
     * @return true si es administrador
     */
    Mono<Boolean> isAdmin();
}

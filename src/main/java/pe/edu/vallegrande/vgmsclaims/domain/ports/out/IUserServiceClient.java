package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Puerto de salida para el cliente del servicio de usuarios
 */
public interface IUserServiceClient {
    
    /**
     * Obtiene información de un usuario por su ID
     * @param userId identificador del usuario
     * @return datos del usuario
     */
    Mono<Map<String, Object>> getUserById(String userId);
    
    /**
     * Obtiene información de un usuario por su email
     * @param email email del usuario
     * @return datos del usuario
     */
    Mono<Map<String, Object>> getUserByEmail(String email);
    
    /**
     * Verifica si un usuario existe
     * @param userId identificador del usuario
     * @return true si existe
     */
    Mono<Boolean> existsUser(String userId);
    
    /**
     * Obtiene usuarios de una organización
     * @param organizationId identificador de la organización
     * @return lista de usuarios
     */
    Mono<java.util.List<Map<String, Object>>> getUsersByOrganization(String organizationId);
}

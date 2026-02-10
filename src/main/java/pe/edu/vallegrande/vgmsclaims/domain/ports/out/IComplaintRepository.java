package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de quejas
 */
public interface IComplaintRepository {
    
    /**
     * Guarda una queja
     * @param complaint queja a guardar
     * @return la queja guardada
     */
    Mono<Complaint> save(Complaint complaint);
    
    /**
     * Busca una queja por su ID
     * @param id identificador de la queja
     * @return la queja encontrada
     */
    Mono<Complaint> findById(String id);
    
    /**
     * Obtiene todas las quejas
     * @return lista de quejas
     */
    Flux<Complaint> findAll();
    
    /**
     * Busca quejas por organización
     * @param organizationId identificador de la organización
     * @return lista de quejas
     */
    Flux<Complaint> findByOrganizationId(String organizationId);
    
    /**
     * Busca quejas por usuario
     * @param userId identificador del usuario
     * @return lista de quejas
     */
    Flux<Complaint> findByUserId(String userId);
    
    /**
     * Busca quejas por estado
     * @param status estado de la queja
     * @return lista de quejas
     */
    Flux<Complaint> findByStatus(String status);
    
    /**
     * Busca quejas por categoría
     * @param categoryId identificador de la categoría
     * @return lista de quejas
     */
    Flux<Complaint> findByCategoryId(String categoryId);
    
    /**
     * Elimina una queja por su ID
     * @param id identificador de la queja
     * @return void
     */
    Mono<Void> deleteById(String id);
    
    /**
     * Verifica si existe una queja con el ID dado
     * @param id identificador de la queja
     * @return true si existe
     */
    Mono<Boolean> existsById(String id);
}

package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de categorías de queja
 */
public interface IComplaintCategoryRepository {
    
    /**
     * Guarda una categoría de queja
     * @param category categoría a guardar
     * @return la categoría guardada
     */
    Mono<ComplaintCategory> save(ComplaintCategory category);
    
    /**
     * Busca una categoría por su ID
     * @param id identificador de la categoría
     * @return la categoría encontrada
     */
    Mono<ComplaintCategory> findById(String id);
    
    /**
     * Obtiene todas las categorías
     * @return lista de categorías
     */
    Flux<ComplaintCategory> findAll();
    
    /**
     * Busca categorías por organización
     * @param organizationId identificador de la organización
     * @return lista de categorías
     */
    Flux<ComplaintCategory> findByOrganizationId(String organizationId);
    
    /**
     * Busca categorías activas por organización
     * @param organizationId identificador de la organización
     * @return lista de categorías activas
     */
    Flux<ComplaintCategory> findActiveByOrganizationId(String organizationId);
    
    /**
     * Busca una categoría por código
     * @param categoryCode código de la categoría
     * @param organizationId identificador de la organización
     * @return la categoría encontrada
     */
    Mono<ComplaintCategory> findByCategoryCodeAndOrganizationId(String categoryCode, String organizationId);
    
    /**
     * Verifica si existe una categoría con el código dado
     * @param categoryCode código de la categoría
     * @param organizationId identificador de la organización
     * @return true si existe
     */
    Mono<Boolean> existsByCategoryCodeAndOrganizationId(String categoryCode, String organizationId);
}

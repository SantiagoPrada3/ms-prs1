package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaintcategory;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de obtener categorías de queja
 */
public interface IGetComplaintCategoryUseCase {
    
    /**
     * Obtiene todas las categorías de queja
     * @return lista de categorías
     */
    Flux<ComplaintCategory> findAll();
    
    /**
     * Obtiene una categoría por su ID
     * @param id identificador de la categoría
     * @return la categoría encontrada
     */
    Mono<ComplaintCategory> findById(String id);
    
    /**
     * Obtiene categorías por organización
     * @param organizationId identificador de la organización
     * @return lista de categorías de la organización
     */
    Flux<ComplaintCategory> findByOrganizationId(String organizationId);
    
    /**
     * Obtiene categorías activas por organización
     * @param organizationId identificador de la organización
     * @return lista de categorías activas
     */
    Flux<ComplaintCategory> findActiveByOrganizationId(String organizationId);
}

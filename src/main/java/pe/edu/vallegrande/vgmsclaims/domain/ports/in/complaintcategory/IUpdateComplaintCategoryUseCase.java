package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaintcategory;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de actualizar categoría de queja
 */
public interface IUpdateComplaintCategoryUseCase {
    
    /**
     * Actualiza una categoría de queja existente
     * @param id identificador de la categoría
     * @param category datos actualizados de la categoría
     * @return la categoría actualizada
     */
    Mono<ComplaintCategory> execute(String id, ComplaintCategory category);
}

package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaintcategory;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de eliminar (soft delete) categoría de queja
 */
public interface IDeleteComplaintCategoryUseCase {
    
    /**
     * Elimina lógicamente una categoría de queja (soft delete)
     * @param id identificador de la categoría
     * @return la categoría eliminada
     */
    Mono<ComplaintCategory> execute(String id);
}

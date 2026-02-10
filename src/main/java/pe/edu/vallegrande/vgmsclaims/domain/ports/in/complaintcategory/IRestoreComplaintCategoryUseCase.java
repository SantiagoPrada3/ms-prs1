package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaintcategory;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de restaurar categoría de queja
 */
public interface IRestoreComplaintCategoryUseCase {
    
    /**
     * Restaura una categoría de queja eliminada
     * @param id identificador de la categoría
     * @return la categoría restaurada
     */
    Mono<ComplaintCategory> execute(String id);
}

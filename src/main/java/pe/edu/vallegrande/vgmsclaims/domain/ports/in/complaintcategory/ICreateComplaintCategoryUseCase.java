package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaintcategory;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintCategory;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de crear categoría de queja
 */
public interface ICreateComplaintCategoryUseCase {
    
    /**
     * Crea una nueva categoría de queja
     * @param category datos de la categoría a crear
     * @return la categoría creada
     */
    Mono<ComplaintCategory> execute(ComplaintCategory category);
}

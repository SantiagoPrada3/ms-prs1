package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de eliminar (soft delete) queja
 */
public interface IDeleteComplaintUseCase {
    
    /**
     * Elimina lógicamente una queja (soft delete)
     * @param id identificador de la queja
     * @return la queja eliminada
     */
    Mono<Complaint> execute(String id);
}

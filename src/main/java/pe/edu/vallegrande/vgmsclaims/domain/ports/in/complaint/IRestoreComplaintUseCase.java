package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de restaurar queja
 */
public interface IRestoreComplaintUseCase {
    
    /**
     * Restaura una queja eliminada
     * @param id identificador de la queja
     * @return la queja restaurada
     */
    Mono<Complaint> execute(String id);
}

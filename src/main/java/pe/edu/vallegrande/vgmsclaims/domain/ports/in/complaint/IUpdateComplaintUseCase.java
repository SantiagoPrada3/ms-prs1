package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de actualizar queja
 */
public interface IUpdateComplaintUseCase {
    
    /**
     * Actualiza una queja existente
     * @param id identificador de la queja
     * @param complaint datos actualizados de la queja
     * @return la queja actualizada
     */
    Mono<Complaint> execute(String id, Complaint complaint);
}

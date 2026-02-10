package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de cerrar queja
 */
public interface ICloseComplaintUseCase {
    
    /**
     * Cierra una queja
     * @param id identificador de la queja
     * @param satisfactionRating calificación de satisfacción (1-5)
     * @return la queja cerrada
     */
    Mono<Complaint> execute(String id, Integer satisfactionRating);
}

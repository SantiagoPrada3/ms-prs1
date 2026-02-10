package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de crear queja
 */
public interface ICreateComplaintUseCase {
    
    /**
     * Crea una nueva queja
     * @param complaint datos de la queja a crear
     * @return la queja creada
     */
    Mono<Complaint> execute(Complaint complaint);
}

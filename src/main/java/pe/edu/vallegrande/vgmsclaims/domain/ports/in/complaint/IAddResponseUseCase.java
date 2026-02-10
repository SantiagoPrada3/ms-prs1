package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintResponse;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de agregar respuesta a queja
 */
public interface IAddResponseUseCase {
    
    /**
     * Agrega una respuesta a una queja
     * @param complaintId identificador de la queja
     * @param response datos de la respuesta
     * @return la queja con la respuesta agregada
     */
    Mono<Complaint> execute(String complaintId, ComplaintResponse response);
}

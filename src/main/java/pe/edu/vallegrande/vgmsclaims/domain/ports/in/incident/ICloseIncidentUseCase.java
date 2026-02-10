package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de cerrar incidente
 */
public interface ICloseIncidentUseCase {
    
    /**
     * Cierra un incidente
     * @param id identificador del incidente
     * @return el incidente cerrado
     */
    Mono<Incident> execute(String id);
}

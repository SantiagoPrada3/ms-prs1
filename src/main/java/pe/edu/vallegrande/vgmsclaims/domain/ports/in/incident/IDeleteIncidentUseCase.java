package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de eliminar (soft delete) incidente
 */
public interface IDeleteIncidentUseCase {
    
    /**
     * Elimina lógicamente un incidente (soft delete)
     * @param id identificador del incidente
     * @return el incidente eliminado
     */
    Mono<Incident> execute(String id);
}

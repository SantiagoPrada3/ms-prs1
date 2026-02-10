package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de eliminar (soft delete) tipo de incidente
 */
public interface IDeleteIncidentTypeUseCase {
    
    /**
     * Elimina lógicamente un tipo de incidente (soft delete)
     * @param id identificador del tipo de incidente
     * @return el tipo de incidente eliminado
     */
    Mono<IncidentType> execute(String id);
}

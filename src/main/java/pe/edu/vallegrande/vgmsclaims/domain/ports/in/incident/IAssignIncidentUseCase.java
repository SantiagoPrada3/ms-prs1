package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de asignar incidente
 */
public interface IAssignIncidentUseCase {
    
    /**
     * Asigna un técnico a un incidente
     * @param incidentId identificador del incidente
     * @param userId identificador del técnico a asignar
     * @return el incidente con la asignación
     */
    Mono<Incident> execute(String incidentId, String userId);
}

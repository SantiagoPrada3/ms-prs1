package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentResolution;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de resolver incidente
 */
public interface IResolveIncidentUseCase {
    
    /**
     * Resuelve un incidente
     * @param incidentId identificador del incidente
     * @param resolution datos de la resolución
     * @return el incidente resuelto
     */
    Mono<Incident> execute(String incidentId, IncidentResolution resolution);
}

package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de crear tipo de incidente
 */
public interface ICreateIncidentTypeUseCase {
    
    /**
     * Crea un nuevo tipo de incidente
     * @param incidentType datos del tipo de incidente a crear
     * @return el tipo de incidente creado
     */
    Mono<IncidentType> execute(IncidentType incidentType);
}

package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de actualizar tipo de incidente
 */
public interface IUpdateIncidentTypeUseCase {
    
    /**
     * Actualiza un tipo de incidente existente
     * @param id identificador del tipo de incidente
     * @param incidentType datos actualizados del tipo de incidente
     * @return el tipo de incidente actualizado
     */
    Mono<IncidentType> execute(String id, IncidentType incidentType);
}

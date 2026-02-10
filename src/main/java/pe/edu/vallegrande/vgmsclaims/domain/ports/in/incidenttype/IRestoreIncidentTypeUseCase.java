package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de restaurar tipo de incidente
 */
public interface IRestoreIncidentTypeUseCase {
    
    /**
     * Restaura un tipo de incidente eliminado
     * @param id identificador del tipo de incidente
     * @return el tipo de incidente restaurado
     */
    Mono<IncidentType> execute(String id);
}

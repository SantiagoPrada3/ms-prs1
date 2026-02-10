package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de restaurar incidente
 */
public interface IRestoreIncidentUseCase {
    
    /**
     * Restaura un incidente eliminado
     * @param id identificador del incidente
     * @return el incidente restaurado
     */
    Mono<Incident> execute(String id);
}

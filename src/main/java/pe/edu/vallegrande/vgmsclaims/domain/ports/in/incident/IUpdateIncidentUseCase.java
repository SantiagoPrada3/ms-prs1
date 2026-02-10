package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de actualizar incidente
 */
public interface IUpdateIncidentUseCase {
    
    /**
     * Actualiza un incidente existente
     * @param id identificador del incidente
     * @param incident datos actualizados del incidente
     * @return el incidente actualizado
     */
    Mono<Incident> execute(String id, Incident incident);
}

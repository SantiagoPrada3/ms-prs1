package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de crear incidente
 */
public interface ICreateIncidentUseCase {
    
    /**
     * Crea un nuevo incidente
     * @param incident datos del incidente a crear
     * @return el incidente creado
     */
    Mono<Incident> execute(Incident incident);
}

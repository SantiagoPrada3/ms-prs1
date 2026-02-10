package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de obtener tipos de incidente
 */
public interface IGetIncidentTypeUseCase {
    
    /**
     * Obtiene todos los tipos de incidente
     * @return lista de tipos de incidente
     */
    Flux<IncidentType> findAll();
    
    /**
     * Obtiene un tipo de incidente por su ID
     * @param id identificador del tipo de incidente
     * @return el tipo de incidente encontrado
     */
    Mono<IncidentType> findById(String id);
    
    /**
     * Obtiene tipos de incidente por organización
     * @param organizationId identificador de la organización
     * @return lista de tipos de incidente de la organización
     */
    Flux<IncidentType> findByOrganizationId(String organizationId);
    
    /**
     * Obtiene tipos de incidente activos por organización
     * @param organizationId identificador de la organización
     * @return lista de tipos de incidente activos
     */
    Flux<IncidentType> findActiveByOrganizationId(String organizationId);
}

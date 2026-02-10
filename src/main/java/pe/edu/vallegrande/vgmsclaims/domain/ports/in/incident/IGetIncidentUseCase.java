package pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de obtener incidentes
 */
public interface IGetIncidentUseCase {
    
    /**
     * Obtiene todos los incidentes
     * @return lista de incidentes
     */
    Flux<Incident> findAll();
    
    /**
     * Obtiene un incidente por su ID
     * @param id identificador del incidente
     * @return el incidente encontrado
     */
    Mono<Incident> findById(String id);
    
    /**
     * Obtiene incidentes por organización
     * @param organizationId identificador de la organización
     * @return lista de incidentes de la organización
     */
    Flux<Incident> findByOrganizationId(String organizationId);
    
    /**
     * Obtiene incidentes por zona
     * @param zoneId identificador de la zona
     * @return lista de incidentes de la zona
     */
    Flux<Incident> findByZoneId(String zoneId);
    
    /**
     * Obtiene incidentes por severidad
     * @param severity severidad del incidente
     * @return lista de incidentes con esa severidad
     */
    Flux<Incident> findBySeverity(String severity);
    
    /**
     * Obtiene incidentes por estado
     * @param status estado del incidente
     * @return lista de incidentes con ese estado
     */
    Flux<Incident> findByStatus(String status);
}

package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de incidentes
 */
public interface IIncidentRepository {
    
    /**
     * Guarda un incidente
     * @param incident incidente a guardar
     * @return el incidente guardado
     */
    Mono<Incident> save(Incident incident);
    
    /**
     * Busca un incidente por su ID
     * @param id identificador del incidente
     * @return el incidente encontrado
     */
    Mono<Incident> findById(String id);
    
    /**
     * Obtiene todos los incidentes
     * @return lista de incidentes
     */
    Flux<Incident> findAll();
    
    /**
     * Busca incidentes por organización
     * @param organizationId identificador de la organización
     * @return lista de incidentes
     */
    Flux<Incident> findByOrganizationId(String organizationId);
    
    /**
     * Busca incidentes por zona
     * @param zoneId identificador de la zona
     * @return lista de incidentes
     */
    Flux<Incident> findByZoneId(String zoneId);
    
    /**
     * Busca incidentes por severidad
     * @param severity severidad del incidente
     * @return lista de incidentes
     */
    Flux<Incident> findBySeverity(String severity);
    
    /**
     * Busca incidentes por estado
     * @param status estado del incidente
     * @return lista de incidentes
     */
    Flux<Incident> findByStatus(String status);
    
    /**
     * Busca incidentes por tipo
     * @param incidentTypeId identificador del tipo
     * @return lista de incidentes
     */
    Flux<Incident> findByIncidentTypeId(String incidentTypeId);
    
    /**
     * Busca incidentes por usuario asignado
     * @param assignedToUserId identificador del usuario asignado
     * @return lista de incidentes
     */
    Flux<Incident> findByAssignedToUserId(String assignedToUserId);
    
    /**
     * Busca incidentes por estado de resolución
     * @param resolved estado de resolución
     * @return lista de incidentes
     */
    Flux<Incident> findByResolved(Boolean resolved);
    
    /**
     * Busca incidentes por estado de registro
     * @param recordStatus estado del registro
     * @return lista de incidentes
     */
    Flux<Incident> findByRecordStatus(String recordStatus);
    
    /**
     * Elimina un incidente por su ID
     * @param id identificador del incidente
     * @return void
     */
    Mono<Void> deleteById(String id);
    
    /**
     * Verifica si existe un incidente con el ID dado
     * @param id identificador del incidente
     * @return true si existe
     */
    Mono<Boolean> existsById(String id);
}

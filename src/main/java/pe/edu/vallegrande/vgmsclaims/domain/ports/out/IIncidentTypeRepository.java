package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de tipos de incidente
 */
public interface IIncidentTypeRepository {
    
    /**
     * Guarda un tipo de incidente
     * @param incidentType tipo de incidente a guardar
     * @return el tipo de incidente guardado
     */
    Mono<IncidentType> save(IncidentType incidentType);
    
    /**
     * Busca un tipo de incidente por su ID
     * @param id identificador del tipo de incidente
     * @return el tipo de incidente encontrado
     */
    Mono<IncidentType> findById(String id);
    
    /**
     * Obtiene todos los tipos de incidente
     * @return lista de tipos de incidente
     */
    Flux<IncidentType> findAll();
    
    /**
     * Busca tipos de incidente por organización
     * @param organizationId identificador de la organización
     * @return lista de tipos de incidente
     */
    Flux<IncidentType> findByOrganizationId(String organizationId);
    
    /**
     * Busca tipos de incidente activos por organización
     * @param organizationId identificador de la organización
     * @return lista de tipos de incidente activos
     */
    Flux<IncidentType> findActiveByOrganizationId(String organizationId);
    
    /**
     * Busca un tipo de incidente por código
     * @param typeCode código del tipo de incidente
     * @param organizationId identificador de la organización
     * @return el tipo de incidente encontrado
     */
    Mono<IncidentType> findByTypeCodeAndOrganizationId(String typeCode, String organizationId);
    
    /**
     * Verifica si existe un tipo de incidente con el código dado
     * @param typeCode código del tipo de incidente
     * @param organizationId identificador de la organización
     * @return true si existe
     */
    Mono<Boolean> existsByTypeCodeAndOrganizationId(String typeCode, String organizationId);
}

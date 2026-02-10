package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentResolution;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de resoluciones de incidente
 */
public interface IIncidentResolutionRepository {
    
    /**
     * Guarda una resolución de incidente
     * @param resolution resolución a guardar
     * @return la resolución guardada
     */
    Mono<IncidentResolution> save(IncidentResolution resolution);
    
    /**
     * Busca una resolución por su ID
     * @param id identificador de la resolución
     * @return la resolución encontrada
     */
    Mono<IncidentResolution> findById(String id);
    
    /**
     * Obtiene la resolución de un incidente
     * @param incidentId identificador del incidente
     * @return la resolución del incidente
     */
    Mono<IncidentResolution> findByIncidentId(String incidentId);
    
    /**
     * Obtiene resoluciones por técnico
     * @param technicianId identificador del técnico
     * @return lista de resoluciones
     */
    Flux<IncidentResolution> findByTechnicianId(String technicianId);
    
    /**
     * Obtiene resoluciones por tipo de resolución
     * @param resolutionType tipo de resolución
     * @return lista de resoluciones
     */
    Flux<IncidentResolution> findByResolutionType(String resolutionType);
    
    /**
     * Verifica si existe una resolución para un incidente
     * @param incidentId identificador del incidente
     * @return true si existe
     */
    Mono<Boolean> existsByIncidentId(String incidentId);
    
    /**
     * Elimina la resolución de un incidente
     * @param incidentId identificador del incidente
     * @return void
     */
    Mono<Void> deleteByIncidentId(String incidentId);
}

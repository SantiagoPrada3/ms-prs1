package pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint;

import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de obtener quejas
 */
public interface IGetComplaintUseCase {
    
    /**
     * Obtiene todas las quejas
     * @return lista de quejas
     */
    Flux<Complaint> findAll();
    
    /**
     * Obtiene una queja por su ID
     * @param id identificador de la queja
     * @return la queja encontrada
     */
    Mono<Complaint> findById(String id);
    
    /**
     * Obtiene quejas por organización
     * @param organizationId identificador de la organización
     * @return lista de quejas de la organización
     */
    Flux<Complaint> findByOrganizationId(String organizationId);
    
    /**
     * Obtiene quejas por usuario
     * @param userId identificador del usuario
     * @return lista de quejas del usuario
     */
    Flux<Complaint> findByUserId(String userId);
    
    /**
     * Obtiene quejas por estado
     * @param status estado de la queja
     * @return lista de quejas con ese estado
     */
    Flux<Complaint> findByStatus(String status);
}

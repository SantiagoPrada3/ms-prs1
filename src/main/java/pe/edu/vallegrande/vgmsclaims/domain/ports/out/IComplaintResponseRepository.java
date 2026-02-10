package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el repositorio de respuestas de queja
 */
public interface IComplaintResponseRepository {
    
    /**
     * Guarda una respuesta de queja
     * @param response respuesta a guardar
     * @return la respuesta guardada
     */
    Mono<ComplaintResponse> save(ComplaintResponse response);
    
    /**
     * Busca una respuesta por su ID
     * @param id identificador de la respuesta
     * @return la respuesta encontrada
     */
    Mono<ComplaintResponse> findById(String id);
    
    /**
     * Obtiene todas las respuestas de una queja
     * @param complaintId identificador de la queja
     * @return lista de respuestas
     */
    Flux<ComplaintResponse> findByComplaintId(String complaintId);
    
    /**
     * Obtiene respuestas por usuario
     * @param userId identificador del usuario que respondió
     * @return lista de respuestas
     */
    Flux<ComplaintResponse> findByUserId(String userId);
    
    /**
     * Cuenta las respuestas de una queja
     * @param complaintId identificador de la queja
     * @return número de respuestas
     */
    Mono<Long> countByComplaintId(String complaintId);
    
    /**
     * Elimina todas las respuestas de una queja
     * @param complaintId identificador de la queja
     * @return void
     */
    Mono<Void> deleteByComplaintId(String complaintId);
}

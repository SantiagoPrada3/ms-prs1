package pe.edu.vallegrande.vgmsclaims.domain.ports.out;

/**
 * Puerto de salida para el publicador de eventos de claims
 */
public interface IClaimsEventPublisher {
    
    /**
     * Publica un evento de queja creada
     * @param event evento a publicar
     */
    void publishComplaintCreated(Object event);
    
    /**
     * Publica un evento de queja actualizada
     * @param event evento a publicar
     */
    void publishComplaintUpdated(Object event);
    
    /**
     * Publica un evento de respuesta agregada a queja
     * @param event evento a publicar
     */
    void publishComplaintResponseAdded(Object event);
    
    /**
     * Publica un evento de queja cerrada
     * @param event evento a publicar
     */
    void publishComplaintClosed(Object event);
    
    /**
     * Publica un evento de incidente creado
     * @param event evento a publicar
     */
    void publishIncidentCreated(Object event);
    
    /**
     * Publica un evento de incidente asignado
     * @param event evento a publicar
     */
    void publishIncidentAssigned(Object event);
    
    /**
     * Publica un evento de incidente actualizado
     * @param event evento a publicar
     */
    void publishIncidentUpdated(Object event);
    
    /**
     * Publica un evento de incidente resuelto
     * @param event evento a publicar
     */
    void publishIncidentResolved(Object event);
    
    /**
     * Publica un evento de incidente cerrado
     * @param event evento a publicar
     */
    void publishIncidentClosed(Object event);
    
    /**
     * Publica una alerta de incidente urgente
     * @param event evento a publicar
     */
    void publishUrgentIncidentAlert(Object event);
}

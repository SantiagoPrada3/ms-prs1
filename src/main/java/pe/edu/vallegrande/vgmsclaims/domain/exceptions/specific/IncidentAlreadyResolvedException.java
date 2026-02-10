package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;

/**
 * Excepción cuando se intenta modificar un incidente que ya está resuelto
 */
public class IncidentAlreadyResolvedException extends BusinessRuleException {
    
    public IncidentAlreadyResolvedException(String incidentId) {
        super("INCIDENT_ALREADY_RESOLVED", 
              String.format("El incidente con id '%s' ya está resuelto y no puede ser modificado", incidentId));
    }
}

package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;

/**
 * Excepción cuando se intenta modificar una queja que ya está cerrada
 */
public class ComplaintAlreadyClosedException extends BusinessRuleException {
    
    public ComplaintAlreadyClosedException(String complaintId) {
        super("COMPLAINT_ALREADY_CLOSED", 
              String.format("La queja con id '%s' ya está cerrada y no puede ser modificada", complaintId));
    }
}

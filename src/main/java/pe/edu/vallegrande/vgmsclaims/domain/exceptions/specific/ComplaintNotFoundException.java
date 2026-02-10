package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.NotFoundException;

/**
 * Excepción específica cuando una queja no es encontrada
 */
public class ComplaintNotFoundException extends NotFoundException {
    
    public ComplaintNotFoundException(String id) {
        super("Queja", id);
    }
    
    public ComplaintNotFoundException(String field, String value, boolean byField) {
        super(String.format("Queja con %s '%s' no encontrada", field, value));
    }
}

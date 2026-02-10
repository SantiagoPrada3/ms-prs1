package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.NotFoundException;

/**
 * Excepción específica cuando un incidente no es encontrado
 */
public class IncidentNotFoundException extends NotFoundException {
    
    public IncidentNotFoundException(String id) {
        super("Incidente", id);
    }
    
    public IncidentNotFoundException(String field, String value, boolean byField) {
        super(String.format("Incidente con %s '%s' no encontrado", field, value));
    }
}

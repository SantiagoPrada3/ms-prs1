package pe.edu.vallegrande.vgmsclaims.domain.exceptions.base;

/**
 * Excepción para conflictos de recursos
 */
public class ConflictException extends DomainException {
    
    public ConflictException(String message) {
        super("CONFLICT", message);
    }
    
    public ConflictException(String resourceType, String identifier) {
        super("CONFLICT", String.format("%s con identificador '%s' ya existe o está en conflicto", resourceType, identifier));
    }
}

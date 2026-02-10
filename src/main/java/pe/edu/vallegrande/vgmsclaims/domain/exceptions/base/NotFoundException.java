package pe.edu.vallegrande.vgmsclaims.domain.exceptions.base;

/**
 * Excepción para recursos no encontrados
 */
public class NotFoundException extends DomainException {
    
    public NotFoundException(String message) {
        super("NOT_FOUND", message);
    }
    
    public NotFoundException(String resourceType, String id) {
        super("NOT_FOUND", String.format("%s con id '%s' no encontrado", resourceType, id));
    }
}

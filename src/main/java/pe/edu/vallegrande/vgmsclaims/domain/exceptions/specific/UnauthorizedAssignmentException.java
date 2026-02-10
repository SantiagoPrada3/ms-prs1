package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;

/**
 * Excepción cuando un usuario no tiene permisos para asignar un recurso
 */
public class UnauthorizedAssignmentException extends BusinessRuleException {
    
    public UnauthorizedAssignmentException(String userId, String resourceType) {
        super("UNAUTHORIZED_ASSIGNMENT", 
              String.format("El usuario '%s' no tiene permisos para asignar el %s", userId, resourceType));
    }
    
    public UnauthorizedAssignmentException(String userId, String resourceType, String resourceId) {
        super("UNAUTHORIZED_ASSIGNMENT", 
              String.format("El usuario '%s' no tiene permisos para asignar el %s con id '%s'", 
                           userId, resourceType, resourceId));
    }
}

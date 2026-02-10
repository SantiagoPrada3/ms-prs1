package pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific;

import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;

/**
 * Excepción cuando se intenta una transición de estado inválida
 */
public class InvalidTransitionException extends BusinessRuleException {
    
    public InvalidTransitionException(String currentState, String targetState) {
        super("INVALID_TRANSITION", 
              String.format("No es posible transicionar del estado '%s' al estado '%s'", currentState, targetState));
    }
    
    public InvalidTransitionException(String entityType, String currentState, String targetState) {
        super("INVALID_TRANSITION", 
              String.format("El %s no puede transicionar del estado '%s' al estado '%s'", 
                           entityType, currentState, targetState));
    }
}

package pe.edu.vallegrande.vgmsclaims.domain.services;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.ISecurityContext;
import reactor.core.publisher.Mono;

/**
 * Servicio de dominio para reglas de autorización de claims
 */
@Service
public class ClaimsAuthorizationService {
    
    private final ISecurityContext securityContext;
    
    public ClaimsAuthorizationService(ISecurityContext securityContext) {
        this.securityContext = securityContext;
    }
    
    /**
     * Verifica si el usuario actual puede ver una queja
     */
    public Mono<Boolean> canViewComplaint(Complaint complaint) {
        return securityContext.isAdmin()
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return Mono.just(true);
                    }
                    return securityContext.getCurrentUserId()
                            .map(userId -> userId.equals(complaint.getUserId()) ||
                                          userId.equals(complaint.getAssignedToUserId()));
                });
    }
    
    /**
     * Verifica si el usuario actual puede modificar una queja
     */
    public Mono<Boolean> canModifyComplaint(Complaint complaint) {
        return securityContext.isAdmin()
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return Mono.just(true);
                    }
                    return securityContext.getCurrentUserId()
                            .map(userId -> userId.equals(complaint.getAssignedToUserId()));
                });
    }
    
    /**
     * Verifica si el usuario actual puede cerrar una queja
     */
    public Mono<Boolean> canCloseComplaint(Complaint complaint) {
        return canModifyComplaint(complaint);
    }
    
    /**
     * Verifica si el usuario actual puede ver un incidente
     */
    public Mono<Boolean> canViewIncident(Incident incident) {
        return securityContext.isAdmin()
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return Mono.just(true);
                    }
                    return securityContext.getCurrentUserId()
                            .map(userId -> userId.equals(incident.getReportedByUserId()) ||
                                          userId.equals(incident.getAssignedToUserId()));
                });
    }
    
    /**
     * Verifica si el usuario actual puede modificar un incidente
     */
    public Mono<Boolean> canModifyIncident(Incident incident) {
        return securityContext.isAdmin()
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return Mono.just(true);
                    }
                    return securityContext.getCurrentUserId()
                            .map(userId -> userId.equals(incident.getAssignedToUserId()));
                });
    }
    
    /**
     * Verifica si el usuario actual puede asignar incidentes
     */
    public Mono<Boolean> canAssignIncident() {
        return securityContext.isAdmin();
    }
    
    /**
     * Verifica si el usuario actual puede resolver un incidente
     */
    public Mono<Boolean> canResolveIncident(Incident incident) {
        return securityContext.isAdmin()
                .flatMap(isAdmin -> {
                    if (isAdmin) {
                        return Mono.just(true);
                    }
                    return securityContext.getCurrentUserId()
                            .map(userId -> userId.equals(incident.getAssignedToUserId()));
                });
    }
    
    /**
     * Verifica si el usuario actual puede cerrar un incidente
     */
    public Mono<Boolean> canCloseIncident(Incident incident) {
        return canModifyIncident(incident);
    }
    
    /**
     * Verifica autorización y lanza excepción si no está autorizado
     */
    public Mono<Void> requireComplaintViewAccess(Complaint complaint) {
        return canViewComplaint(complaint)
                .flatMap(allowed -> {
                    if (!allowed) {
                        return Mono.error(new BusinessRuleException("ACCESS_DENIED", 
                                "No tiene permiso para ver esta queja"));
                    }
                    return Mono.empty();
                });
    }
    
    /**
     * Verifica autorización y lanza excepción si no está autorizado
     */
    public Mono<Void> requireIncidentViewAccess(Incident incident) {
        return canViewIncident(incident)
                .flatMap(allowed -> {
                    if (!allowed) {
                        return Mono.error(new BusinessRuleException("ACCESS_DENIED", 
                                "No tiene permiso para ver este incidente"));
                    }
                    return Mono.empty();
                });
    }
}

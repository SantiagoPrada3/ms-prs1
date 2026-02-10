package pe.edu.vallegrande.vgmsclaims.application.usecases.incident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident.IAssignIncidentUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IClaimsEventPublisher;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentRepository;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IUserServiceClient;
import pe.edu.vallegrande.vgmsclaims.application.events.incident.IncidentAssignedEvent;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Implementación del caso de uso para asignar incidentes
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AssignIncidentUseCaseImpl implements IAssignIncidentUseCase {
    
    private final IIncidentRepository incidentRepository;
    private final IClaimsEventPublisher eventPublisher;
    private final IUserServiceClient userServiceClient;
    
    @Override
    public Mono<Incident> execute(String incidentId, String userId) {
        log.info("Asignando incidente {} al usuario {}", incidentId, userId);
        
        return incidentRepository.findById(incidentId)
                .switchIfEmpty(Mono.error(new IncidentNotFoundException(incidentId)))
                .flatMap(incident -> {
                    if (!incident.canBeAssigned()) {
                        return Mono.error(new BusinessRuleException(
                                "INVALID_STATE",
                                "El incidente no puede ser asignado en su estado actual"));
                    }
                    
                    // Verificar que el usuario existe
                    return userServiceClient.existsUser(userId)
                            .flatMap(exists -> {
                                if (!exists) {
                                    return Mono.error(new BusinessRuleException(
                                            "USER_NOT_FOUND",
                                            "El usuario asignado no existe"));
                                }
                                
                                incident.assignTo(userId);
                                return incidentRepository.save(incident);
                            });
                })
                .doOnSuccess(saved -> {
                    log.info("Incidente asignado: {} -> {}", saved.getIncidentCode(), userId);
                    publishIncidentAssignedEvent(saved);
                })
                .doOnError(error -> log.error("Error al asignar incidente: {}", error.getMessage()));
    }
    
    private void publishIncidentAssignedEvent(Incident incident) {
        try {
            IncidentAssignedEvent event = IncidentAssignedEvent.builder()
                    .incidentId(incident.getId())
                    .incidentCode(incident.getIncidentCode())
                    .assignedToUserId(incident.getAssignedToUserId())
                    .severity(incident.getSeverity() != null ? incident.getSeverity().name() : null)
                    .assignedAt(Instant.now())
                    .build();
            eventPublisher.publishIncidentAssigned(event);
        } catch (Exception e) {
            log.warn("No se pudo publicar evento de incidente asignado: {}", e.getMessage());
        }
    }
}

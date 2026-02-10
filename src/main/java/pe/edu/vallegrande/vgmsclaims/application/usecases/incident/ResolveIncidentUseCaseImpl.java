package pe.edu.vallegrande.vgmsclaims.application.usecases.incident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.base.BusinessRuleException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentAlreadyResolvedException;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentResolution;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident.IResolveIncidentUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IClaimsEventPublisher;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentRepository;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.ISecurityContext;
import pe.edu.vallegrande.vgmsclaims.application.events.incident.IncidentResolvedEvent;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Implementación del caso de uso para resolver incidentes
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResolveIncidentUseCaseImpl implements IResolveIncidentUseCase {
    
    private final IIncidentRepository incidentRepository;
    private final IClaimsEventPublisher eventPublisher;
    private final ISecurityContext securityContext;
    
    @Override
    public Mono<Incident> execute(String incidentId, IncidentResolution resolution) {
        log.info("Resolviendo incidente: {}", incidentId);
        
        return securityContext.getCurrentUserId()
                .flatMap(userId -> incidentRepository.findById(incidentId)
                        .switchIfEmpty(Mono.error(new IncidentNotFoundException(incidentId)))
                        .flatMap(incident -> {
                            if (incident.getResolved() != null && incident.getResolved()) {
                                return Mono.error(new IncidentAlreadyResolvedException(incidentId));
                            }
                            
                            if (!incident.canBeResolved()) {
                                return Mono.error(new BusinessRuleException(
                                        "INVALID_STATE",
                                        "El incidente no puede ser resuelto en su estado actual"));
                            }
                            
                            incident.resolve(userId, resolution.getResolutionNotes());
                            return incidentRepository.save(incident);
                        }))
                .doOnSuccess(saved -> {
                    log.info("Incidente resuelto: {}", saved.getIncidentCode());
                    publishIncidentResolvedEvent(saved, resolution);
                })
                .doOnError(error -> log.error("Error al resolver incidente: {}", error.getMessage()));
    }
    
    private void publishIncidentResolvedEvent(Incident incident, IncidentResolution resolution) {
        try {
            IncidentResolvedEvent event = IncidentResolvedEvent.builder()
                    .incidentId(incident.getId())
                    .incidentCode(incident.getIncidentCode())
                    .resolvedByUserId(incident.getResolvedByUserId())
                    .resolutionType(resolution.getResolutionType() != null ? 
                            resolution.getResolutionType().name() : null)
                    .totalCost(resolution.getTotalCost())
                    .resolvedAt(Instant.now())
                    .build();
            eventPublisher.publishIncidentResolved(event);
        } catch (Exception e) {
            log.warn("No se pudo publicar evento de incidente resuelto: {}", e.getMessage());
        }
    }
}

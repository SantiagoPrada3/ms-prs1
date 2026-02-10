package pe.edu.vallegrande.vgmsclaims.application.usecases.incident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident.IUpdateIncidentUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IClaimsEventPublisher;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentRepository;
import pe.edu.vallegrande.vgmsclaims.application.events.incident.IncidentUpdatedEvent;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Implementación del caso de uso para actualizar incidentes
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateIncidentUseCaseImpl implements IUpdateIncidentUseCase {
    
    private final IIncidentRepository incidentRepository;
    private final IClaimsEventPublisher eventPublisher;
    
    @Override
    public Mono<Incident> execute(String id, Incident updatedData) {
        log.info("Actualizando incidente con ID: {}", id);
        
        return incidentRepository.findById(id)
                .switchIfEmpty(Mono.error(new IncidentNotFoundException(id)))
                .flatMap(existing -> {
                    // Actualizar campos permitidos
                    if (updatedData.getTitle() != null) {
                        existing.setTitle(updatedData.getTitle());
                    }
                    if (updatedData.getDescription() != null) {
                        existing.setDescription(updatedData.getDescription());
                    }
                    if (updatedData.getSeverity() != null) {
                        existing.setSeverity(updatedData.getSeverity());
                    }
                    if (updatedData.getStatus() != null) {
                        existing.setStatus(updatedData.getStatus());
                    }
                    if (updatedData.getAffectedBoxesCount() != null) {
                        existing.setAffectedBoxesCount(updatedData.getAffectedBoxesCount());
                    }
                    if (updatedData.getResolutionNotes() != null) {
                        existing.setResolutionNotes(updatedData.getResolutionNotes());
                    }
                    
                    existing.setUpdatedAt(Instant.now());
                    
                    return incidentRepository.save(existing);
                })
                .doOnSuccess(saved -> {
                    log.info("Incidente actualizado: {}", saved.getIncidentCode());
                    publishIncidentUpdatedEvent(saved);
                })
                .doOnError(error -> log.error("Error al actualizar incidente: {}", error.getMessage()));
    }
    
    private void publishIncidentUpdatedEvent(Incident incident) {
        try {
            IncidentUpdatedEvent event = IncidentUpdatedEvent.builder()
                    .incidentId(incident.getId())
                    .incidentCode(incident.getIncidentCode())
                    .status(incident.getStatus() != null ? incident.getStatus().name() : null)
                    .severity(incident.getSeverity() != null ? incident.getSeverity().name() : null)
                    .updatedAt(incident.getUpdatedAt())
                    .build();
            eventPublisher.publishIncidentUpdated(event);
        } catch (Exception e) {
            log.warn("No se pudo publicar evento de incidente actualizado: {}", e.getMessage());
        }
    }
}

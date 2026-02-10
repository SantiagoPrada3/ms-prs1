package pe.edu.vallegrande.vgmsclaims.application.usecases.complaint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ComplaintStatus;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.RecordStatus;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.ICreateComplaintUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IClaimsEventPublisher;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IComplaintRepository;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.ISecurityContext;
import pe.edu.vallegrande.vgmsclaims.application.events.complaint.ComplaintCreatedEvent;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementación del caso de uso para crear quejas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateComplaintUseCaseImpl implements ICreateComplaintUseCase {
    
    private final IComplaintRepository complaintRepository;
    private final IClaimsEventPublisher eventPublisher;
    private final ISecurityContext securityContext;
    
    @Override
    public Mono<Complaint> execute(Complaint complaint) {
        log.info("Creando nueva queja: {}", complaint.getSubject());
        
        return securityContext.getCurrentUserId()
                .flatMap(userId -> {
                    // Establecer valores por defecto
                    complaint.setComplaintCode(generateComplaintCode());
                    complaint.setUserId(userId);
                    complaint.setStatus(ComplaintStatus.RECEIVED);
                    complaint.setRecordStatus(RecordStatus.ACTIVE);
                    complaint.setCreatedAt(Instant.now());
                    complaint.setComplaintDate(Instant.now());
                    
                    return complaintRepository.save(complaint)
                            .doOnSuccess(saved -> {
                                log.info("Queja creada exitosamente: {}", saved.getComplaintCode());
                                publishComplaintCreatedEvent(saved);
                            })
                            .doOnError(error -> log.error("Error al crear queja: {}", error.getMessage()));
                });
    }
    
    private String generateComplaintCode() {
        return "QUE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private void publishComplaintCreatedEvent(Complaint complaint) {
        try {
            ComplaintCreatedEvent event = ComplaintCreatedEvent.builder()
                    .complaintId(complaint.getId())
                    .complaintCode(complaint.getComplaintCode())
                    .userId(complaint.getUserId())
                    .subject(complaint.getSubject())
                    .priority(complaint.getPriority() != null ? complaint.getPriority().name() : null)
                    .createdAt(complaint.getCreatedAt())
                    .build();
            eventPublisher.publishComplaintCreated(event);
        } catch (Exception e) {
            log.warn("No se pudo publicar evento de queja creada: {}", e.getMessage());
        }
    }
}

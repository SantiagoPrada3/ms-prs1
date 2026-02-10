package pe.edu.vallegrande.vgmsclaims.application.usecases.complaint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.models.ComplaintResponse;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IClaimsEventPublisher;
import pe.edu.vallegrande.vgmsclaims.application.events.complaint.ComplaintResponseAddedEvent;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementación del caso de uso para agregar respuestas a quejas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddResponseUseCaseImpl {
    
    private final IClaimsEventPublisher eventPublisher;
    
    public Mono<ComplaintResponse> execute(String complaintId, ComplaintResponse response) {
        log.info("Agregando respuesta a queja: {}", complaintId);
        
        response.setId(UUID.randomUUID().toString());
        response.setComplaintId(complaintId);
        response.setResponseDate(Instant.now());
        response.setCreatedAt(Instant.now());
        
        // Aquí iría la lógica de persistencia de la respuesta
        // Por ahora retornamos la respuesta configurada
        
        publishResponseAddedEvent(response);
        
        return Mono.just(response);
    }
    
    private void publishResponseAddedEvent(ComplaintResponse response) {
        try {
            ComplaintResponseAddedEvent event = ComplaintResponseAddedEvent.builder()
                    .responseId(response.getId())
                    .complaintId(response.getComplaintId())
                    .responseType(response.getResponseType() != null ? response.getResponseType().name() : null)
                    .respondedByUserId(response.getRespondedByUserId())
                    .createdAt(response.getCreatedAt())
                    .build();
            eventPublisher.publishComplaintResponseAdded(event);
        } catch (Exception e) {
            log.warn("No se pudo publicar evento de respuesta agregada: {}", e.getMessage());
        }
    }
}

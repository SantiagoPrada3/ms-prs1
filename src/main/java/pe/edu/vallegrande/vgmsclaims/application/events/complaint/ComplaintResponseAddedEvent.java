package pe.edu.vallegrande.vgmsclaims.application.events.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Evento emitido cuando se agrega una respuesta a una queja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponseAddedEvent {
    
    private String responseId;
    private String complaintId;
    private String responseType;
    private String respondedByUserId;
    private Instant createdAt;
}

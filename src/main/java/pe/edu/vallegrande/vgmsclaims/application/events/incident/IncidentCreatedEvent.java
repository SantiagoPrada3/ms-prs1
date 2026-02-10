package pe.edu.vallegrande.vgmsclaims.application.events.incident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Evento emitido cuando se crea un incidente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentCreatedEvent {
    
    private String incidentId;
    private String incidentCode;
    private String title;
    private String severity;
    private String zoneId;
    private String reportedByUserId;
    private Instant createdAt;
}

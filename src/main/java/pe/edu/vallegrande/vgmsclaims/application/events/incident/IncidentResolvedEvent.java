package pe.edu.vallegrande.vgmsclaims.application.events.incident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Evento emitido cuando se resuelve un incidente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentResolvedEvent {
    
    private String incidentId;
    private String incidentCode;
    private String resolvedByUserId;
    private String resolutionType;
    private BigDecimal totalCost;
    private Instant resolvedAt;
}

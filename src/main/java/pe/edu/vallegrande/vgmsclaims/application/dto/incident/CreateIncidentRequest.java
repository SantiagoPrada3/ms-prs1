package pe.edu.vallegrande.vgmsclaims.application.dto.incident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * DTO para crear un nuevo incidente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentRequest {
    
    private String incidentCode;
    
    @NotBlank(message = "El título es requerido")
    private String title;
    
    @NotBlank(message = "La descripción es requerida")
    private String description;
    
    @NotNull(message = "El tipo de incidente es requerido")
    private String incidentTypeId;
    
    private String incidentCategory;
    
    @NotNull(message = "La zona es requerida")
    private String zoneId;
    
    private Instant incidentDate;
    
    @NotNull(message = "La severidad es requerida")
    private String severity;
    
    private String status;
    
    private Integer affectedBoxesCount;
    
    private String organizationId;
    
    private String reportedByUserId;
    
    private String assignedToUserId;
    
    private String resolvedByUserId;
    
    private Boolean resolved;
    
    private String resolutionNotes;
    
    private String recordStatus;
}

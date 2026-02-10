package pe.edu.vallegrande.vgmsclaims.application.dto.incidenttype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un tipo de incidente existente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncidentTypeRequest {
    
    private String typeName;
    private String description;
    private String priorityLevel;
    private Integer estimatedResolutionTime;
    private Boolean requiresExternalService;
}

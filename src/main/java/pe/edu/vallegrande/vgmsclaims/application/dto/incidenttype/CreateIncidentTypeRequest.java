package pe.edu.vallegrande.vgmsclaims.application.dto.incidenttype;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear un nuevo tipo de incidente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateIncidentTypeRequest {
    
    @NotBlank(message = "El código de tipo es requerido")
    private String typeCode;
    
    @NotBlank(message = "El nombre de tipo es requerido")
    private String typeName;
    
    private String description;
    
    private String priorityLevel;
    
    private Integer estimatedResolutionTime;
    
    private Boolean requiresExternalService;
    
    private String organizationId;
}

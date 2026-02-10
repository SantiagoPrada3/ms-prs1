package pe.edu.vallegrande.vgmsclaims.application.dto.incident;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para asignar un incidente a un técnico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignIncidentRequest {
    
    @NotBlank(message = "El ID del usuario es requerido")
    private String userId;
    
    private String notes;
}

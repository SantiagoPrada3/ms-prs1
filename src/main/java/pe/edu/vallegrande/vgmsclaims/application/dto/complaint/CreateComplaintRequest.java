package pe.edu.vallegrande.vgmsclaims.application.dto.complaint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear una nueva queja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintRequest {
    
    @NotBlank(message = "El asunto es requerido")
    private String subject;
    
    @NotBlank(message = "La descripción es requerida")
    private String description;
    
    @NotNull(message = "La categoría es requerida")
    private String categoryId;
    
    private String waterBoxId;
    private String priority;
    private String organizationId;
}

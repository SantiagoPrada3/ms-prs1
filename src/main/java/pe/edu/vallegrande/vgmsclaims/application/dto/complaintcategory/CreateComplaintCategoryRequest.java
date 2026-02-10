package pe.edu.vallegrande.vgmsclaims.application.dto.complaintcategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear una nueva categoría de queja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintCategoryRequest {
    
    @NotBlank(message = "El código de categoría es requerido")
    private String categoryCode;
    
    @NotBlank(message = "El nombre de categoría es requerido")
    private String categoryName;
    
    private String description;
    
    private String priorityLevel;
    
    private Integer maxResponseTime;
    
    private String organizationId;
}

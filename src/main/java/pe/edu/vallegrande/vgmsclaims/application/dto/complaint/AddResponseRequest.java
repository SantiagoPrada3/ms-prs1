package pe.edu.vallegrande.vgmsclaims.application.dto.complaint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para agregar una respuesta a una queja
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddResponseRequest {
    
    @NotNull(message = "El tipo de respuesta es requerido")
    private String responseType;
    
    @NotBlank(message = "El mensaje es requerido")
    private String message;
    
    private String internalNotes;
}

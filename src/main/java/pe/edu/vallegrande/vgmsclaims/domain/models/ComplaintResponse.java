package pe.edu.vallegrande.vgmsclaims.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ResponseType;

import java.time.Instant;

/**
 * Entidad de dominio que representa una Respuesta a Queja.
 * No contiene anotaciones de infraestructura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintResponse {
    
    private String id;
    private String complaintId;
    private Instant responseDate;
    private ResponseType responseType;
    private String message;
    private String respondedByUserId;
    private String internalNotes;
    private Instant createdAt;
    private Instant updatedAt;
}

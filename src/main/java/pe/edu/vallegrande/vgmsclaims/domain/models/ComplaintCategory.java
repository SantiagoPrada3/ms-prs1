package pe.edu.vallegrande.vgmsclaims.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.RecordStatus;

import java.time.Instant;

/**
 * Entidad de dominio que representa una Categoría de Queja.
 * No contiene anotaciones de infraestructura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintCategory {
    
    private String id;
    private String organizationId;
    private String categoryCode;
    private String categoryName;
    private String description;
    private String priorityLevel;
    private Integer maxResponseTime; // hours
    
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
    
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Verifica si la categoría está activa
     */
    public boolean isActive() {
        return recordStatus == RecordStatus.ACTIVE;
    }
}

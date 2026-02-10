package pe.edu.vallegrande.vgmsclaims.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.MaterialUsed;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ResolutionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad de dominio que representa una Resolución de Incidente.
 * No contiene anotaciones de infraestructura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentResolution {
    
    private String id;
    private String incidentId;
    private Instant resolutionDate;
    private ResolutionType resolutionType;
    private String actionsTaken;
    
    @Builder.Default
    private List<MaterialUsed> materialsUsed = new ArrayList<>();
    
    private Integer laborHours;
    private BigDecimal totalCost;
    private String resolvedByUserId;
    private Boolean qualityCheck;
    private Boolean followUpRequired;
    private String resolutionNotes;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Calcula el costo total de los materiales usados
     */
    public BigDecimal calculateMaterialsCost() {
        if (materialsUsed == null || materialsUsed.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return materialsUsed.stream()
                .map(MaterialUsed::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Agrega un material usado a la resolución
     */
    public void addMaterial(MaterialUsed material) {
        if (materialsUsed == null) {
            materialsUsed = new ArrayList<>();
        }
        materialsUsed.add(material);
    }
}

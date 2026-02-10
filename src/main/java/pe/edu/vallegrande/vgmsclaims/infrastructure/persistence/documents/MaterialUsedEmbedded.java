package pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Documento embebido que representa un material usado en una resolución de incidente.
 * Se almacena como parte de IncidentResolutionDocument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUsedEmbedded {
    
    private String productId;
    private Integer quantity;
    private String unit;
    private BigDecimal unitCost;
    
    /**
     * Calcula el costo total del material
     */
    public BigDecimal getTotalCost() {
        if (quantity == null || unitCost == null) {
            return BigDecimal.ZERO;
        }
        return unitCost.multiply(BigDecimal.valueOf(quantity));
    }
}

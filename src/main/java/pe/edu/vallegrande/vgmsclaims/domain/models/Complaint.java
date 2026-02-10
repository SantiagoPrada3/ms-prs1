package pe.edu.vallegrande.vgmsclaims.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ComplaintPriority;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ComplaintStatus;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.RecordStatus;

import java.time.Instant;

/**
 * Entidad de dominio que representa una Queja/Reclamo.
 * No contiene anotaciones de infraestructura (MongoDB, JPA, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    
    private String id;
    private String organizationId;
    private String complaintCode;
    private String userId;
    private String categoryId;
    private String waterBoxId;
    private Instant complaintDate;
    private String subject;
    private String description;
    private ComplaintPriority priority;
    private ComplaintStatus status;
    private String assignedToUserId;
    private Instant expectedResolutionDate;
    private Instant actualResolutionDate;
    private Integer satisfactionRating;
    
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
    
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Verifica si la queja puede ser asignada
     */
    public boolean canBeAssigned() {
        return status == ComplaintStatus.RECEIVED && recordStatus == RecordStatus.ACTIVE;
    }

    /**
     * Verifica si la queja puede ser resuelta
     */
    public boolean canBeResolved() {
        return (status == ComplaintStatus.IN_PROGRESS || status == ComplaintStatus.RECEIVED) 
                && recordStatus == RecordStatus.ACTIVE;
    }

    /**
     * Verifica si la queja puede ser cerrada
     */
    public boolean canBeClosed() {
        return status == ComplaintStatus.RESOLVED && recordStatus == RecordStatus.ACTIVE;
    }

    /**
     * Verifica si la queja está activa
     */
    public boolean isActive() {
        return recordStatus == RecordStatus.ACTIVE;
    }

    /**
     * Asigna un usuario a la queja
     */
    public void assignTo(String userId) {
        if (!canBeAssigned()) {
            throw new IllegalStateException("La queja no puede ser asignada en su estado actual");
        }
        this.assignedToUserId = userId;
        this.status = ComplaintStatus.IN_PROGRESS;
        this.updatedAt = Instant.now();
    }

    /**
     * Resuelve la queja
     */
    public void resolve() {
        if (!canBeResolved()) {
            throw new IllegalStateException("La queja no puede ser resuelta en su estado actual");
        }
        this.status = ComplaintStatus.RESOLVED;
        this.actualResolutionDate = Instant.now();
        this.updatedAt = Instant.now();
    }

    /**
     * Cierra la queja
     */
    public void close(Integer satisfactionRating) {
        if (!canBeClosed()) {
            throw new IllegalStateException("La queja no puede ser cerrada en su estado actual");
        }
        this.status = ComplaintStatus.CLOSED;
        this.satisfactionRating = satisfactionRating;
        this.updatedAt = Instant.now();
    }
}

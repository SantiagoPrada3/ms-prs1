package pe.edu.vallegrande.vgmsclaims.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.IncidentSeverity;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.IncidentStatus;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.RecordStatus;

import java.time.Instant;

/**
 * Entidad de dominio que representa un Incidente de infraestructura.
 * No contiene anotaciones de infraestructura (MongoDB, JPA, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incident {
    
    private String id;
    private String organizationId;
    private String incidentCode;
    private String incidentTypeId;
    private String incidentCategory;
    private String zoneId;
    private Instant incidentDate;
    private String title;
    private String description;
    private IncidentSeverity severity;
    private IncidentStatus status;
    private Integer affectedBoxesCount;
    private String reportedByUserId;
    private String assignedToUserId;
    private String resolvedByUserId;
    
    @Builder.Default
    private Boolean resolved = false;
    
    private String resolutionNotes;
    
    @Builder.Default
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
    
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Verifica si el incidente es crítico
     */
    public boolean isCritical() {
        return severity == IncidentSeverity.CRITICAL;
    }

    /**
     * Verifica si el incidente está activo
     */
    public boolean isActive() {
        return recordStatus == RecordStatus.ACTIVE;
    }

    /**
     * Verifica si el incidente puede ser asignado
     */
    public boolean canBeAssigned() {
        return status == IncidentStatus.REPORTED && isActive();
    }

    /**
     * Verifica si el incidente puede ser resuelto
     */
    public boolean canBeResolved() {
        return (status == IncidentStatus.ASSIGNED || status == IncidentStatus.IN_PROGRESS) && isActive();
    }

    /**
     * Verifica si el incidente puede ser cerrado
     */
    public boolean canBeClosed() {
        return status == IncidentStatus.RESOLVED && isActive();
    }

    /**
     * Asigna un técnico al incidente
     */
    public void assignTo(String userId) {
        if (!canBeAssigned()) {
            throw new IllegalStateException("El incidente no puede ser asignado en su estado actual");
        }
        this.assignedToUserId = userId;
        this.status = IncidentStatus.ASSIGNED;
        this.updatedAt = Instant.now();
    }

    /**
     * Marca el incidente como en progreso
     */
    public void startProgress() {
        if (status != IncidentStatus.ASSIGNED) {
            throw new IllegalStateException("El incidente debe estar asignado para iniciar progreso");
        }
        this.status = IncidentStatus.IN_PROGRESS;
        this.updatedAt = Instant.now();
    }

    /**
     * Resuelve el incidente
     */
    public void resolve(String userId, String notes) {
        if (!canBeResolved()) {
            throw new IllegalStateException("El incidente no puede ser resuelto en su estado actual");
        }
        this.resolvedByUserId = userId;
        this.resolutionNotes = notes;
        this.resolved = true;
        this.status = IncidentStatus.RESOLVED;
        this.updatedAt = Instant.now();
    }

    /**
     * Cierra el incidente
     */
    public void close() {
        if (!canBeClosed()) {
            throw new IllegalStateException("El incidente no puede ser cerrado en su estado actual");
        }
        this.status = IncidentStatus.CLOSED;
        this.updatedAt = Instant.now();
    }
}

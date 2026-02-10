package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.RecordStatus;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentTypeRepository;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentTypeDocument;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentTypeMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del puerto de repositorio de tipos de incidente usando MongoDB
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IncidentTypeRepositoryImpl implements IIncidentTypeRepository {

    private final IncidentTypeMongoRepository typeRepository;

    @Override
    public Mono<IncidentType> save(IncidentType incidentType) {
        log.debug("Guardando tipo de incidente: {}", incidentType.getId());
        IncidentTypeDocument document = toDocument(incidentType);
        document.prePersist();
        return typeRepository.save(document)
                .map(this::toDomain);
    }

    @Override
    public Mono<IncidentType> findById(String id) {
        log.debug("Buscando tipo de incidente por ID: {}", id);
        return typeRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Flux<IncidentType> findAll() {
        log.debug("Buscando todos los tipos de incidente");
        return typeRepository.findAll()
                .map(this::toDomain);
    }

    @Override
    public Flux<IncidentType> findByOrganizationId(String organizationId) {
        log.debug("Buscando tipos de incidente por organización: {}", organizationId);
        return typeRepository.findByOrganizationId(organizationId)
                .map(this::toDomain);
    }

    @Override
    public Flux<IncidentType> findActiveByOrganizationId(String organizationId) {
        log.debug("Buscando tipos de incidente activos por organización: {}", organizationId);
        return typeRepository.findByOrganizationId(organizationId)
                .filter(doc -> RecordStatus.ACTIVE.name().equals(doc.getRecordStatus()))
                .map(this::toDomain);
    }

    @Override
    public Mono<IncidentType> findByTypeCodeAndOrganizationId(String typeCode, String organizationId) {
        log.debug("Buscando tipo de incidente por código {} y organización {}", typeCode, organizationId);
        return typeRepository.findByOrganizationId(organizationId)
                .filter(doc -> typeCode.equals(doc.getTypeCode()))
                .next()
                .map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByTypeCodeAndOrganizationId(String typeCode, String organizationId) {
        log.debug("Verificando existencia de tipo con código {} en organización {}", typeCode, organizationId);
        return typeRepository.findByOrganizationId(organizationId)
                .filter(doc -> typeCode.equals(doc.getTypeCode()))
                .hasElements();
    }

    // ========== Métodos de mapeo ==========

    private IncidentType toDomain(IncidentTypeDocument document) {
        return IncidentType.builder()
                .id(document.getId())
                .organizationId(document.getOrganizationId())
                .typeCode(document.getTypeCode())
                .typeName(document.getTypeName())
                .description(document.getDescription())
                .priorityLevel(document.getPriorityLevel())
                .estimatedResolutionTime(document.getEstimatedResolutionTime())
                .requiresExternalService(document.getRequiresExternalService())
                .recordStatus(parseRecordStatus(document.getRecordStatus()))
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }

    private IncidentTypeDocument toDocument(IncidentType domain) {
        return IncidentTypeDocument.builder()
                .id(domain.getId())
                .organizationId(domain.getOrganizationId())
                .typeCode(domain.getTypeCode())
                .typeName(domain.getTypeName())
                .description(domain.getDescription())
                .priorityLevel(domain.getPriorityLevel())
                .estimatedResolutionTime(domain.getEstimatedResolutionTime())
                .requiresExternalService(domain.getRequiresExternalService())
                .recordStatus(domain.getRecordStatus() != null ? domain.getRecordStatus().name() : null)
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    private RecordStatus parseRecordStatus(String status) {
        if (status == null) return null;
        try {
            return RecordStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

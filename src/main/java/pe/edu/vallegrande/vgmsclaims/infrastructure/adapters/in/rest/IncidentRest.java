package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsclaims.application.dto.common.ApiResponse;
import pe.edu.vallegrande.vgmsclaims.application.dto.incident.AssignIncidentRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.incident.CreateIncidentRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.incident.ResolveIncidentRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.incident.UpdateIncidentRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.incident.IncidentResponse;
import pe.edu.vallegrande.vgmsclaims.application.mappers.IncidentMapper;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentResolution;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.IncidentSeverity;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.IncidentStatus;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.MaterialUsed;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ResolutionType;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident.*;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentMongoRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestión de incidentes
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/admin/incidents")
@RequiredArgsConstructor
@Tag(name = "Incidents", description = "API para gestión de incidentes")
public class IncidentRest {
    
    private final ICreateIncidentUseCase createIncidentUseCase;
    private final IGetIncidentUseCase getIncidentUseCase;
    private final IUpdateIncidentUseCase updateIncidentUseCase;
    private final IAssignIncidentUseCase assignIncidentUseCase;
    private final IResolveIncidentUseCase resolveIncidentUseCase;
    private final ICloseIncidentUseCase closeIncidentUseCase;
    private final IncidentMapper incidentMapper;
    private final IncidentMongoRepository incidentMongoRepository;
    
    @Operation(summary = "Listar todos los incidentes")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de incidentes obtenida exitosamente")
    })
    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findAll() {
        log.info("Obteniendo todos los incidentes");
        return getIncidentUseCase.findAll()
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }
    
    @Operation(summary = "Obtener incidente por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> findById(
            @Parameter(description = "ID del incidente") @PathVariable String id) {
        log.info("Buscando incidente con ID: {}", id);
        return getIncidentUseCase.findById(id)
                .map(incidentMapper::toResponse)
                .map(incident -> ResponseEntity.ok(ApiResponse.success(incident)));
    }
    
    @Operation(summary = "Crear nuevo incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Incidente creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> create(
            @Valid @RequestBody CreateIncidentRequest request) {
        log.info("Creando nuevo incidente: {}", request.getTitle());
        Incident incident = incidentMapper.toDomain(request);
        return createIncidentUseCase.execute(incident)
                .map(incidentMapper::toResponse)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success(created, "Incidente creado exitosamente")));
    }
    
    @Operation(summary = "Actualizar incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente actualizado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> update(
            @Parameter(description = "ID del incidente") @PathVariable String id,
            @Valid @RequestBody UpdateIncidentRequest request) {
        log.info("Actualizando incidente con ID: {}", id);
        Incident updatedData = Incident.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .severity(request.getSeverity() != null ? 
                        IncidentSeverity.valueOf(request.getSeverity().toUpperCase()) : null)
                .status(request.getStatus() != null ? 
                        IncidentStatus.valueOf(request.getStatus().toUpperCase()) : null)
                .affectedBoxesCount(request.getAffectedBoxesCount())
                .resolutionNotes(request.getResolutionNotes())
                .build();
        return updateIncidentUseCase.execute(id, updatedData)
                .map(incidentMapper::toResponse)
                .map(updated -> ResponseEntity.ok(ApiResponse.success(updated, "Incidente actualizado exitosamente")));
    }
    
    @Operation(summary = "Asignar incidente a técnico")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente asignado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "No se puede asignar en el estado actual")
    })
    @PostMapping("/{id}/assign")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> assign(
            @Parameter(description = "ID del incidente") @PathVariable String id,
            @Valid @RequestBody AssignIncidentRequest request) {
        log.info("Asignando incidente {} al usuario {}", id, request.getUserId());
        return assignIncidentUseCase.execute(id, request.getUserId())
                .map(incidentMapper::toResponse)
                .map(assigned -> ResponseEntity.ok(ApiResponse.success(assigned, "Incidente asignado exitosamente")));
    }
    
    @Operation(summary = "Resolver incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente resuelto exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Incidente ya resuelto")
    })
    @PostMapping("/{id}/resolve")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> resolve(
            @Parameter(description = "ID del incidente") @PathVariable String id,
            @Valid @RequestBody ResolveIncidentRequest request) {
        log.info("Resolviendo incidente: {}", id);
        
        List<MaterialUsed> materials = null;
        if (request.getMaterialsUsed() != null) {
            materials = request.getMaterialsUsed().stream()
                    .map(m -> new MaterialUsed(m.getProductId(), m.getQuantity(), m.getUnit(), m.getUnitCost()))
                    .collect(Collectors.toList());
        }
        
        IncidentResolution resolution = IncidentResolution.builder()
                .resolutionType(request.getResolutionType() != null ? 
                        ResolutionType.valueOf(request.getResolutionType().toUpperCase()) : null)
                .actionsTaken(request.getActionsTaken())
                .materialsUsed(materials)
                .laborHours(request.getLaborHours())
                .totalCost(request.getTotalCost())
                .qualityCheck(request.getQualityCheck())
                .followUpRequired(request.getFollowUpRequired())
                .resolutionNotes(request.getResolutionNotes())
                .build();
        
        return resolveIncidentUseCase.execute(id, resolution)
                .map(incidentMapper::toResponse)
                .map(resolved -> ResponseEntity.ok(ApiResponse.success(resolved, "Incidente resuelto exitosamente")));
    }
    
    @Operation(summary = "Cerrar incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente cerrado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "No se puede cerrar en el estado actual")
    })
    @PostMapping("/{id}/close")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> close(
            @Parameter(description = "ID del incidente") @PathVariable String id) {
        log.info("Cerrando incidente con ID: {}", id);
        return closeIncidentUseCase.execute(id)
                .map(incidentMapper::toResponse)
                .map(closed -> ResponseEntity.ok(ApiResponse.success(closed, "Incidente cerrado exitosamente")));
    }
    
    @Operation(summary = "Buscar incidentes por organización")
    @GetMapping("/organization/{organizationId}")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findByOrganization(
            @Parameter(description = "ID de la organización") @PathVariable String organizationId) {
        log.info("Buscando incidentes de organización: {}", organizationId);
        return getIncidentUseCase.findByOrganizationId(organizationId)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }
    
    @Operation(summary = "Buscar incidentes por zona")
    @GetMapping("/zone/{zoneId}")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findByZone(
            @Parameter(description = "ID de la zona") @PathVariable String zoneId) {
        log.info("Buscando incidentes de zona: {}", zoneId);
        return getIncidentUseCase.findByZoneId(zoneId)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }
    
    @Operation(summary = "Buscar incidentes por severidad")
    @GetMapping("/severity/{severity}")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findBySeverity(
            @Parameter(description = "Severidad del incidente") @PathVariable String severity) {
        log.info("Buscando incidentes con severidad: {}", severity);
        return getIncidentUseCase.findBySeverity(severity)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }
    
    @Operation(summary = "Buscar incidentes por estado")
    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findByStatus(
            @Parameter(description = "Estado del incidente") @PathVariable String status) {
        log.info("Buscando incidentes con estado: {}", status);
        return getIncidentUseCase.findByStatus(status)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }

    @Operation(summary = "Eliminar incidente (lógico)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente eliminado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> delete(
            @Parameter(description = "ID del incidente") @PathVariable String id) {
        log.info("Eliminando lógicamente incidente con ID: {}", id);
        return incidentMongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setRecordStatus("INACTIVE");
                    existing.setUpdatedAt(Instant.now());
                    return incidentMongoRepository.save(existing);
                })
                .map(incidentMapper::toDomain)
                .map(incidentMapper::toResponse)
                .map(deleted -> ResponseEntity.ok(ApiResponse.success(deleted, "Incidente eliminado exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Restaurar incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incidente restaurado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Incidente no encontrado")
    })
    @PatchMapping("/{id}/restore")
    public Mono<ResponseEntity<ApiResponse<IncidentResponse>>> restore(
            @Parameter(description = "ID del incidente") @PathVariable String id) {
        log.info("Restaurando incidente con ID: {}", id);
        return incidentMongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setRecordStatus("ACTIVE");
                    existing.setUpdatedAt(Instant.now());
                    return incidentMongoRepository.save(existing);
                })
                .map(incidentMapper::toDomain)
                .map(incidentMapper::toResponse)
                .map(restored -> ResponseEntity.ok(ApiResponse.success(restored, "Incidente restaurado exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar incidentes activos")
    @GetMapping("/active")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findAllActive() {
        log.info("Obteniendo todos los incidentes activos");
        return incidentMongoRepository.findByRecordStatus("ACTIVE")
                .map(incidentMapper::toDomain)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }

    @Operation(summary = "Listar incidentes inactivos")
    @GetMapping("/inactive")
    public Mono<ResponseEntity<ApiResponse<List<IncidentResponse>>>> findAllInactive() {
        log.info("Obteniendo todos los incidentes inactivos");
        return incidentMongoRepository.findByRecordStatus("INACTIVE")
                .map(incidentMapper::toDomain)
                .map(incidentMapper::toResponse)
                .collectList()
                .map(incidents -> ResponseEntity.ok(ApiResponse.success(incidents)));
    }
}

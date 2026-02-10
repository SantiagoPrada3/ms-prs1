package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsclaims.application.dto.common.ApiResponse;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentTypeDocument;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentTypeMongoRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

/**
 * Controlador REST para gestión de tipos de incidentes
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/incident-types")
@RequiredArgsConstructor
@Tag(name = "Incident Types", description = "API para gestión de tipos de incidentes")
public class IncidentTypeRest {

    private final IncidentTypeMongoRepository incidentTypeRepository;

    @Operation(summary = "Listar todos los tipos de incidentes")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de tipos obtenida exitosamente")
    })
    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<IncidentTypeDocument>>>> findAll() {
        log.info("Obteniendo todos los tipos de incidentes");
        return incidentTypeRepository.findAll()
                .collectList()
                .map(types -> ResponseEntity.ok(ApiResponse.success(types)));
    }

    @Operation(summary = "Obtener tipo de incidente por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tipo encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentTypeDocument>>> findById(
            @Parameter(description = "ID del tipo de incidente") @PathVariable String id) {
        log.info("Buscando tipo de incidente con ID: {}", id);
        return incidentTypeRepository.findById(id)
                .map(type -> ResponseEntity.ok(ApiResponse.success(type)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo tipo de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tipo creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Mono<ResponseEntity<ApiResponse<IncidentTypeDocument>>> create(
            @RequestBody IncidentTypeDocument request) {
        log.info("Creando nuevo tipo de incidente: {}", request.getTypeName());
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(Instant.now());
        if (request.getStatus() == null) {
            request.setStatus("ACTIVE");
        }
        if (request.getRecordStatus() == null) {
            request.setRecordStatus("ACTIVE");
        }
        return incidentTypeRepository.save(request)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success(created, "Tipo de incidente creado exitosamente")));
    }

    @Operation(summary = "Actualizar tipo de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tipo actualizado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentTypeDocument>>> update(
            @Parameter(description = "ID del tipo de incidente") @PathVariable String id,
            @RequestBody IncidentTypeDocument request) {
        log.info("Actualizando tipo de incidente con ID: {}", id);
        return incidentTypeRepository.findById(id)
                .flatMap(existing -> {
                    request.setId(id);
                    request.setCreatedAt(existing.getCreatedAt());
                    request.setUpdatedAt(Instant.now());
                    return incidentTypeRepository.save(request);
                })
                .map(updated -> ResponseEntity.ok(ApiResponse.success(updated, "Tipo de incidente actualizado exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar tipo de incidente (lógico)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tipo eliminado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentTypeDocument>>> delete(
            @Parameter(description = "ID del tipo de incidente") @PathVariable String id) {
        log.info("Eliminando lógicamente tipo de incidente con ID: {}", id);
        return incidentTypeRepository.findById(id)
                .flatMap(existing -> {
                    existing.setRecordStatus("INACTIVE");
                    existing.setStatus("INACTIVE");
                    existing.setUpdatedAt(Instant.now());
                    return incidentTypeRepository.save(existing);
                })
                .map(deleted -> ResponseEntity.ok(ApiResponse.success(deleted, "Tipo de incidente eliminado exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Restaurar tipo de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tipo restaurado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @PatchMapping("/{id}/restore")
    public Mono<ResponseEntity<ApiResponse<IncidentTypeDocument>>> restore(
            @Parameter(description = "ID del tipo de incidente") @PathVariable String id) {
        log.info("Restaurando tipo de incidente con ID: {}", id);
        return incidentTypeRepository.findById(id)
                .flatMap(existing -> {
                    existing.setRecordStatus("ACTIVE");
                    existing.setStatus("ACTIVE");
                    existing.setUpdatedAt(Instant.now());
                    return incidentTypeRepository.save(existing);
                })
                .map(restored -> ResponseEntity.ok(ApiResponse.success(restored, "Tipo de incidente restaurado exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar tipos de incidentes por organización")
    @GetMapping("/organization/{organizationId}")
    public Mono<ResponseEntity<ApiResponse<List<IncidentTypeDocument>>>> findByOrganization(
            @Parameter(description = "ID de la organización") @PathVariable String organizationId) {
        log.info("Buscando tipos de incidentes de organización: {}", organizationId);
        return incidentTypeRepository.findByOrganizationId(organizationId)
                .collectList()
                .map(types -> ResponseEntity.ok(ApiResponse.success(types)));
    }

    @Operation(summary = "Listar tipos de incidentes activos")
    @GetMapping("/active")
    public Mono<ResponseEntity<ApiResponse<List<IncidentTypeDocument>>>> findAllActive() {
        log.info("Obteniendo todos los tipos de incidentes activos");
        return incidentTypeRepository.findByRecordStatus("ACTIVE")
                .collectList()
                .map(types -> ResponseEntity.ok(ApiResponse.success(types)));
    }

    @Operation(summary = "Listar tipos de incidentes inactivos")
    @GetMapping("/inactive")
    public Mono<ResponseEntity<ApiResponse<List<IncidentTypeDocument>>>> findAllInactive() {
        log.info("Obteniendo todos los tipos de incidentes inactivos");
        return incidentTypeRepository.findByRecordStatus("INACTIVE")
                .collectList()
                .map(types -> ResponseEntity.ok(ApiResponse.success(types)));
    }
}

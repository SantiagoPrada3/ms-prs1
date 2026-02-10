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
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentResolutionDocument;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentResolutionMongoRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

/**
 * Controlador REST para gestión de resoluciones de incidentes
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/incident-resolutions")
@RequiredArgsConstructor
@Tag(name = "Incident Resolutions", description = "API para gestión de resoluciones de incidentes")
public class IncidentResolutionRest {

    private final IncidentResolutionMongoRepository incidentResolutionRepository;

    @Operation(summary = "Listar todas las resoluciones de incidentes")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de resoluciones obtenida exitosamente")
    })
    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<IncidentResolutionDocument>>>> findAll() {
        log.info("Obteniendo todas las resoluciones de incidentes");
        return incidentResolutionRepository.findAll()
                .collectList()
                .map(resolutions -> ResponseEntity.ok(ApiResponse.success(resolutions)));
    }

    @Operation(summary = "Obtener resolución de incidente por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Resolución encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resolución no encontrada")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentResolutionDocument>>> findById(
            @Parameter(description = "ID de la resolución") @PathVariable String id) {
        log.info("Buscando resolución de incidente con ID: {}", id);
        return incidentResolutionRepository.findById(id)
                .map(resolution -> ResponseEntity.ok(ApiResponse.success(resolution)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener resolución por ID del incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Resolución encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resolución no encontrada")
    })
    @GetMapping("/incident/{incidentId}")
    public Mono<ResponseEntity<ApiResponse<IncidentResolutionDocument>>> findByIncidentId(
            @Parameter(description = "ID del incidente") @PathVariable String incidentId) {
        log.info("Buscando resolución del incidente: {}", incidentId);
        return incidentResolutionRepository.findByIncidentId(incidentId)
                .map(resolution -> ResponseEntity.ok(ApiResponse.success(resolution)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva resolución de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Resolución creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Mono<ResponseEntity<ApiResponse<IncidentResolutionDocument>>> create(
            @RequestBody IncidentResolutionDocument request) {
        log.info("Creando nueva resolución de incidente para: {}", request.getIncidentId());
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(Instant.now());
        if (request.getResolutionDate() == null) {
            request.setResolutionDate(Instant.now());
        }
        return incidentResolutionRepository.save(request)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success(created, "Resolución de incidente creada exitosamente")));
    }

    @Operation(summary = "Actualizar resolución de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Resolución actualizada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resolución no encontrada")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<IncidentResolutionDocument>>> update(
            @Parameter(description = "ID de la resolución") @PathVariable String id,
            @RequestBody IncidentResolutionDocument request) {
        log.info("Actualizando resolución de incidente con ID: {}", id);
        return incidentResolutionRepository.findById(id)
                .flatMap(existing -> {
                    request.setId(id);
                    request.setCreatedAt(existing.getCreatedAt());
                    request.setUpdatedAt(Instant.now());
                    return incidentResolutionRepository.save(request);
                })
                .map(updated -> ResponseEntity.ok(ApiResponse.success(updated, "Resolución de incidente actualizada exitosamente")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar resolución de incidente")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Resolución eliminada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resolución no encontrada")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(
            @Parameter(description = "ID de la resolución") @PathVariable String id) {
        log.info("Eliminando resolución de incidente con ID: {}", id);
        return incidentResolutionRepository.findById(id)
                .flatMap(existing -> incidentResolutionRepository.deleteById(id)
                        .then(Mono.just(ResponseEntity.ok(ApiResponse.<Void>success(null, "Resolución de incidente eliminada exitosamente")))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

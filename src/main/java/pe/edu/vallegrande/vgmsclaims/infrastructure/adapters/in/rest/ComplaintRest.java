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
import pe.edu.vallegrande.vgmsclaims.application.dto.complaint.CreateComplaintRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.complaint.UpdateComplaintRequest;
import pe.edu.vallegrande.vgmsclaims.application.dto.complaint.ComplaintResponse;
import pe.edu.vallegrande.vgmsclaims.application.mappers.ComplaintMapper;
import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ComplaintPriority;
import pe.edu.vallegrande.vgmsclaims.domain.models.valueobjects.ComplaintStatus;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.ICloseComplaintUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.ICreateComplaintUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.IGetComplaintUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.IUpdateComplaintUseCase;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para gestión de quejas
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
@Tag(name = "Complaints", description = "API para gestión de quejas")
public class ComplaintRest {
    
    private final ICreateComplaintUseCase createComplaintUseCase;
    private final IGetComplaintUseCase getComplaintUseCase;
    private final IUpdateComplaintUseCase updateComplaintUseCase;
    private final ICloseComplaintUseCase closeComplaintUseCase;
    private final ComplaintMapper complaintMapper;
    
    @Operation(summary = "Listar todas las quejas")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de quejas obtenida exitosamente")
    })
    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<ComplaintResponse>>>> findAll() {
        log.info("Obteniendo todas las quejas");
        return getComplaintUseCase.findAll()
                .map(complaintMapper::toResponse)
                .collectList()
                .map(complaints -> ResponseEntity.ok(ApiResponse.success(complaints)));
    }
    
    @Operation(summary = "Obtener queja por ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Queja encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Queja no encontrada")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<ComplaintResponse>>> findById(
            @Parameter(description = "ID de la queja") @PathVariable String id) {
        log.info("Buscando queja con ID: {}", id);
        return getComplaintUseCase.findById(id)
                .map(complaintMapper::toResponse)
                .map(complaint -> ResponseEntity.ok(ApiResponse.success(complaint)));
    }
    
    @Operation(summary = "Crear nueva queja")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Queja creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Mono<ResponseEntity<ApiResponse<ComplaintResponse>>> create(
            @Valid @RequestBody CreateComplaintRequest request) {
        log.info("Creando nueva queja: {}", request.getSubject());
        Complaint complaint = complaintMapper.toDomain(request);
        return createComplaintUseCase.execute(complaint)
                .map(complaintMapper::toResponse)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success(created, "Queja creada exitosamente")));
    }
    
    @Operation(summary = "Actualizar queja")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Queja actualizada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Queja no encontrada")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<ComplaintResponse>>> update(
            @Parameter(description = "ID de la queja") @PathVariable String id,
            @Valid @RequestBody UpdateComplaintRequest request) {
        log.info("Actualizando queja con ID: {}", id);
        Complaint updatedData = Complaint.builder()
                .subject(request.getSubject())
                .description(request.getDescription())
                .priority(request.getPriority() != null ? 
                        ComplaintPriority.valueOf(request.getPriority().toUpperCase()) : null)
                .status(request.getStatus() != null ? 
                        ComplaintStatus.valueOf(request.getStatus().toUpperCase()) : null)
                .assignedToUserId(request.getAssignedToUserId())
                .expectedResolutionDate(request.getExpectedResolutionDate())
                .build();
        return updateComplaintUseCase.execute(id, updatedData)
                .map(complaintMapper::toResponse)
                .map(updated -> ResponseEntity.ok(ApiResponse.success(updated, "Queja actualizada exitosamente")));
    }
    
    @Operation(summary = "Cerrar queja")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Queja cerrada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Queja no encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Queja ya cerrada")
    })
    @PostMapping("/{id}/close")
    public Mono<ResponseEntity<ApiResponse<ComplaintResponse>>> close(
            @Parameter(description = "ID de la queja") @PathVariable String id,
            @Parameter(description = "Calificación de satisfacción (1-5)") 
            @RequestParam(required = false) Integer satisfactionRating) {
        log.info("Cerrando queja con ID: {}", id);
        return closeComplaintUseCase.execute(id, satisfactionRating)
                .map(complaintMapper::toResponse)
                .map(closed -> ResponseEntity.ok(ApiResponse.success(closed, "Queja cerrada exitosamente")));
    }
    
    @Operation(summary = "Buscar quejas por organización")
    @GetMapping("/organization/{organizationId}")
    public Mono<ResponseEntity<ApiResponse<List<ComplaintResponse>>>> findByOrganization(
            @Parameter(description = "ID de la organización") @PathVariable String organizationId) {
        log.info("Buscando quejas de organización: {}", organizationId);
        return getComplaintUseCase.findByOrganizationId(organizationId)
                .map(complaintMapper::toResponse)
                .collectList()
                .map(complaints -> ResponseEntity.ok(ApiResponse.success(complaints)));
    }
    
    @Operation(summary = "Buscar quejas por usuario")
    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<ApiResponse<List<ComplaintResponse>>>> findByUser(
            @Parameter(description = "ID del usuario") @PathVariable String userId) {
        log.info("Buscando quejas del usuario: {}", userId);
        return getComplaintUseCase.findByUserId(userId)
                .map(complaintMapper::toResponse)
                .collectList()
                .map(complaints -> ResponseEntity.ok(ApiResponse.success(complaints)));
    }
    
    @Operation(summary = "Buscar quejas por estado")
    @GetMapping("/status/{status}")
    public Mono<ResponseEntity<ApiResponse<List<ComplaintResponse>>>> findByStatus(
            @Parameter(description = "Estado de la queja") @PathVariable String status) {
        log.info("Buscando quejas con estado: {}", status);
        return getComplaintUseCase.findByStatus(status)
                .map(complaintMapper::toResponse)
                .collectList()
                .map(complaints -> ResponseEntity.ok(ApiResponse.success(complaints)));
    }
}

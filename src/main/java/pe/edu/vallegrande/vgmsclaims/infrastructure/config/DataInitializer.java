package pe.edu.vallegrande.vgmsclaims.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentResolutionDocument;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentTypeDocument;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentResolutionMongoRepository;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.IncidentTypeMongoRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Inicializador de datos de prueba para desarrollo
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final IncidentTypeMongoRepository incidentTypeRepository;
    private final IncidentResolutionMongoRepository incidentResolutionRepository;

    @Bean
    @Profile("dev")
    public CommandLineRunner initData() {
        return args -> {
            initIncidentTypes();
            initIncidentResolutions();
        };
    }

    private void initIncidentTypes() {
        incidentTypeRepository.count()
                .filter(count -> count == 0)
                .flatMapMany(count -> {
                    log.info("Inicializando tipos de incidentes...");
                    
                    List<IncidentTypeDocument> types = Arrays.asList(
                            IncidentTypeDocument.builder()
                                    .typeCode("FUGA_TUBERIA")
                                    .typeName("Fuga de Tubería")
                                    .description("Fuga de agua en tuberías principales o secundarias")
                                    .priorityLevel("HIGH")
                                    .estimatedResolutionTime(4)
                                    .requiresExternalService(false)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("CORTE_SUMINISTRO")
                                    .typeName("Corte de Suministro")
                                    .description("Interrupción del servicio de agua potable")
                                    .priorityLevel("CRITICAL")
                                    .estimatedResolutionTime(2)
                                    .requiresExternalService(false)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("BAJA_PRESION")
                                    .typeName("Baja Presión")
                                    .description("Presión de agua insuficiente en el servicio")
                                    .priorityLevel("MEDIUM")
                                    .estimatedResolutionTime(6)
                                    .requiresExternalService(false)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("AGUA_TURBIA")
                                    .typeName("Agua Turbia")
                                    .description("Calidad del agua deficiente - presencia de sedimentos")
                                    .priorityLevel("HIGH")
                                    .estimatedResolutionTime(8)
                                    .requiresExternalService(true)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("MEDIDOR_DANADO")
                                    .typeName("Medidor Dañado")
                                    .description("Medidor de agua dañado o con funcionamiento irregular")
                                    .priorityLevel("MEDIUM")
                                    .estimatedResolutionTime(3)
                                    .requiresExternalService(false)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("ROTURA_MATRIZ")
                                    .typeName("Rotura de Matriz")
                                    .description("Rotura en la matriz principal de distribución")
                                    .priorityLevel("CRITICAL")
                                    .estimatedResolutionTime(12)
                                    .requiresExternalService(true)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("CONEXION_CLANDESTINA")
                                    .typeName("Conexión Clandestina")
                                    .description("Detección de conexión no autorizada")
                                    .priorityLevel("HIGH")
                                    .estimatedResolutionTime(4)
                                    .requiresExternalService(false)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentTypeDocument.builder()
                                    .typeCode("OLOR_DESAGRADABLE")
                                    .typeName("Olor Desagradable")
                                    .description("Presencia de olores extraños en el agua")
                                    .priorityLevel("HIGH")
                                    .estimatedResolutionTime(24)
                                    .requiresExternalService(true)
                                    .recordStatus("ACTIVE")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build()
                    );
                    
                    return incidentTypeRepository.saveAll(types);
                })
                .collectList()
                .subscribe(
                        types -> {
                            if (!types.isEmpty()) {
                                log.info("Se crearon {} tipos de incidentes", types.size());
                            }
                        },
                        error -> log.error("Error al inicializar tipos de incidentes: {}", error.getMessage())
                );
    }

    private void initIncidentResolutions() {
        incidentResolutionRepository.count()
                .filter(count -> count == 0)
                .flatMapMany(count -> {
                    log.info("Inicializando resoluciones de incidentes de ejemplo...");
                    
                    List<IncidentResolutionDocument> resolutions = Arrays.asList(
                            IncidentResolutionDocument.builder()
                                    .incidentId("sample-incident-001")
                                    .resolutionDate(Instant.now().minusSeconds(86400))
                                    .resolutionType("REPARACION")
                                    .actionsTaken("Se reparó la fuga en tubería de PVC de 2 pulgadas")
                                    .materialsUsed(Arrays.asList(
                                            IncidentResolutionDocument.MaterialUsedEmbedded.builder()
                                                    .productId("PROD001")
                                                    .quantity(2)
                                                    .unit("UNIDAD")
                                                    .unitCost(new BigDecimal("25.50"))
                                                    .build(),
                                            IncidentResolutionDocument.MaterialUsedEmbedded.builder()
                                                    .productId("PROD002")
                                                    .quantity(1)
                                                    .unit("METRO")
                                                    .unitCost(new BigDecimal("15.00"))
                                                    .build()
                                    ))
                                    .laborHours(3)
                                    .totalCost(new BigDecimal("120.00"))
                                    .resolvedByUserId("tech-user-001")
                                    .qualityCheck(true)
                                    .followUpRequired(false)
                                    .resolutionNotes("Reparación completada satisfactoriamente")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build(),
                            
                            IncidentResolutionDocument.builder()
                                    .incidentId("sample-incident-002")
                                    .resolutionDate(Instant.now().minusSeconds(172800))
                                    .resolutionType("REEMPLAZO")
                                    .actionsTaken("Se reemplazó el medidor dañado por uno nuevo")
                                    .materialsUsed(Arrays.asList(
                                            IncidentResolutionDocument.MaterialUsedEmbedded.builder()
                                                    .productId("MED001")
                                                    .quantity(1)
                                                    .unit("UNIDAD")
                                                    .unitCost(new BigDecimal("150.00"))
                                                    .build()
                                    ))
                                    .laborHours(2)
                                    .totalCost(new BigDecimal("200.00"))
                                    .resolvedByUserId("tech-user-002")
                                    .qualityCheck(true)
                                    .followUpRequired(true)
                                    .resolutionNotes("Se requiere verificación de lectura en 30 días")
                                    .createdAt(Instant.now())
                                    .updatedAt(Instant.now())
                                    .build()
                    );
                    
                    return incidentResolutionRepository.saveAll(resolutions);
                })
                .collectList()
                .subscribe(
                        resolutions -> {
                            if (!resolutions.isEmpty()) {
                                log.info("Se crearon {} resoluciones de incidentes de ejemplo", resolutions.size());
                            }
                        },
                        error -> log.error("Error al inicializar resoluciones: {}", error.getMessage())
                );
    }
}

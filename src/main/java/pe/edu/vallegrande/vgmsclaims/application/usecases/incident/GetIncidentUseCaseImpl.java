package pe.edu.vallegrande.vgmsclaims.application.usecases.incident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.IncidentNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Incident;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incident.IGetIncidentUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para obtener incidentes
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetIncidentUseCaseImpl implements IGetIncidentUseCase {
    
    private final IIncidentRepository incidentRepository;
    
    @Override
    public Flux<Incident> findAll() {
        log.info("Obteniendo todos los incidentes");
        return incidentRepository.findAll()
                .doOnComplete(() -> log.info("Consulta de incidentes completada"));
    }
    
    @Override
    public Mono<Incident> findById(String id) {
        log.info("Buscando incidente con ID: {}", id);
        return incidentRepository.findById(id)
                .switchIfEmpty(Mono.error(new IncidentNotFoundException(id)))
                .doOnSuccess(incident -> log.info("Incidente encontrado: {}", incident.getIncidentCode()));
    }
    
    @Override
    public Flux<Incident> findByOrganizationId(String organizationId) {
        log.info("Buscando incidentes de organización: {}", organizationId);
        return incidentRepository.findByOrganizationId(organizationId);
    }
    
    @Override
    public Flux<Incident> findByZoneId(String zoneId) {
        log.info("Buscando incidentes de zona: {}", zoneId);
        return incidentRepository.findByZoneId(zoneId);
    }
    
    @Override
    public Flux<Incident> findBySeverity(String severity) {
        log.info("Buscando incidentes con severidad: {}", severity);
        return incidentRepository.findBySeverity(severity);
    }
    
    @Override
    public Flux<Incident> findByStatus(String status) {
        log.info("Buscando incidentes con estado: {}", status);
        return incidentRepository.findByStatus(status);
    }
}

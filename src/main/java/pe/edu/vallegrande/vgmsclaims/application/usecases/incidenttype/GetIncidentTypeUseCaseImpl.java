package pe.edu.vallegrande.vgmsclaims.application.usecases.incidenttype;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.models.IncidentType;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.incidenttype.IGetIncidentTypeUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IIncidentTypeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso de obtener tipos de incidente
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetIncidentTypeUseCaseImpl implements IGetIncidentTypeUseCase {
    
    private final IIncidentTypeRepository incidentTypeRepository;
    
    @Override
    public Flux<IncidentType> findAll() {
        return incidentTypeRepository.findAll();
    }
    
    @Override
    public Mono<IncidentType> findById(String id) {
        return incidentTypeRepository.findById(id);
    }
    
    @Override
    public Flux<IncidentType> findByOrganizationId(String organizationId) {
        return incidentTypeRepository.findByOrganizationId(organizationId);
    }
    
    @Override
    public Flux<IncidentType> findActiveByOrganizationId(String organizationId) {
        return incidentTypeRepository.findActiveByOrganizationId(organizationId);
    }
}

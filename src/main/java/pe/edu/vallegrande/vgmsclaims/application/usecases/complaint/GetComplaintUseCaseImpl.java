package pe.edu.vallegrande.vgmsclaims.application.usecases.complaint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsclaims.domain.exceptions.specific.ComplaintNotFoundException;
import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.ports.in.complaint.IGetComplaintUseCase;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IComplaintRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para obtener quejas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetComplaintUseCaseImpl implements IGetComplaintUseCase {
    
    private final IComplaintRepository complaintRepository;
    
    @Override
    public Flux<Complaint> findAll() {
        log.info("Obteniendo todas las quejas");
        return complaintRepository.findAll()
                .doOnComplete(() -> log.info("Consulta de quejas completada"));
    }
    
    @Override
    public Mono<Complaint> findById(String id) {
        log.info("Buscando queja con ID: {}", id);
        return complaintRepository.findById(id)
                .switchIfEmpty(Mono.error(new ComplaintNotFoundException(id)))
                .doOnSuccess(complaint -> log.info("Queja encontrada: {}", complaint.getComplaintCode()));
    }
    
    @Override
    public Flux<Complaint> findByOrganizationId(String organizationId) {
        log.info("Buscando quejas de organización: {}", organizationId);
        return complaintRepository.findByOrganizationId(organizationId);
    }
    
    @Override
    public Flux<Complaint> findByUserId(String userId) {
        log.info("Buscando quejas del usuario: {}", userId);
        return complaintRepository.findByUserId(userId);
    }
    
    @Override
    public Flux<Complaint> findByStatus(String status) {
        log.info("Buscando quejas con estado: {}", status);
        return complaintRepository.findByStatus(status);
    }
}

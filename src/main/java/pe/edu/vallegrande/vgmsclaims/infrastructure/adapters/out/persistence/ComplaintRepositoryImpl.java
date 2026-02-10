package pe.edu.vallegrande.vgmsclaims.infrastructure.adapters.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vgmsclaims.application.mappers.ComplaintMapper;
import pe.edu.vallegrande.vgmsclaims.domain.models.Complaint;
import pe.edu.vallegrande.vgmsclaims.domain.ports.out.IComplaintRepository;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories.ComplaintMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del puerto de repositorio de quejas usando MongoDB
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ComplaintRepositoryImpl implements IComplaintRepository {

    private final ComplaintMongoRepository complaintRepository;
    private final ComplaintMapper mapper;

    @Override
    public Mono<Complaint> save(Complaint complaint) {
        log.debug("Guardando queja: {}", complaint.getId());
        return complaintRepository.save(mapper.toDocument(complaint))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Complaint> findById(String id) {
        log.debug("Buscando queja por ID: {}", id);
        return complaintRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Complaint> findAll() {
        log.debug("Buscando todas las quejas");
        return complaintRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Complaint> findByOrganizationId(String organizationId) {
        log.debug("Buscando quejas por organización: {}", organizationId);
        return complaintRepository.findByOrganizationId(organizationId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Complaint> findByUserId(String userId) {
        log.debug("Buscando quejas por usuario: {}", userId);
        return complaintRepository.findByUserId(userId)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Complaint> findByStatus(String status) {
        log.debug("Buscando quejas por estado: {}", status);
        return complaintRepository.findByStatus(status)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Complaint> findByCategoryId(String categoryId) {
        log.debug("Buscando quejas por categoría: {}", categoryId);
        return complaintRepository.findByCategoryId(categoryId)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        log.debug("Eliminando queja: {}", id);
        return complaintRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return complaintRepository.existsById(id);
    }
}

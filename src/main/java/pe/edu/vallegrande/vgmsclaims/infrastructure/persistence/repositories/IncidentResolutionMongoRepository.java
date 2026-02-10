package pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.IncidentResolutionDocument;
import reactor.core.publisher.Mono;

/**
 * Repositorio MongoDB reactivo para resoluciones de incidentes
 */
@Repository
public interface IncidentResolutionMongoRepository extends ReactiveMongoRepository<IncidentResolutionDocument, String> {
    
    Mono<IncidentResolutionDocument> findByIncidentId(String incidentId);
}

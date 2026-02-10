package pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vgmsclaims.infrastructure.persistence.documents.ComplaintResponseDocument;
import reactor.core.publisher.Flux;

/**
 * Repositorio MongoDB reactivo para respuestas a quejas
 */
@Repository
public interface ComplaintResponseMongoRepository extends ReactiveMongoRepository<ComplaintResponseDocument, String> {
    
    Flux<ComplaintResponseDocument> findByComplaintId(String complaintId);
}

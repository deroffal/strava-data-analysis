package fr.deroffal.stravastatistics.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SummaryRepository extends MongoRepository<SummaryActivityDocument, Long> {
  Optional<SummaryActivityDocument> findTopByOrderByStartDateDesc();
}

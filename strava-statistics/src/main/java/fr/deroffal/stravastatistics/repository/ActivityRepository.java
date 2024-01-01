package fr.deroffal.stravastatistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<ActivityDocument, Long> {
}

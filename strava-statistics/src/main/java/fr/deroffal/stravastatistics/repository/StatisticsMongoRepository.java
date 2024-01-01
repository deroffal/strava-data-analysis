package fr.deroffal.stravastatistics.repository;

import fr.deroffal.stravastatistics.app.StatisticsRepository;
import fr.deroffal.stravastatistics.client.model.DetailedActivity;
import fr.deroffal.stravastatistics.client.model.SummaryActivity;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StatisticsMongoRepository implements StatisticsRepository {

  private final SummaryRepository summaryRepository;
  private final ActivityRepository activityRepository;
  private final MongoMapper mongoMapper;

  public StatisticsMongoRepository(SummaryRepository summaryRepository, ActivityRepository activityRepository,
      MongoMapper mongoMapper) {
    this.summaryRepository = summaryRepository;
    this.activityRepository = activityRepository;
    this.mongoMapper = mongoMapper;
  }

  @Override
  public Optional<Instant> getLastRecordedActivityDate() {
    return summaryRepository.findTopByOrderByStartDateDesc()
        .map(SummaryDocument::getStartDate)
        .map(OffsetDateTime::toInstant);
  }

  @Override
  public SummaryActivity saveSummary(SummaryActivity summaryActivity) {
    return mongoMapper.from(summaryRepository.save(mongoMapper.from(summaryActivity)));
  }

  @Override
  public DetailedActivity saveActivity(DetailedActivity detailedActivity) {
    return mongoMapper.from(activityRepository.save(mongoMapper.from(detailedActivity)));
  }
}

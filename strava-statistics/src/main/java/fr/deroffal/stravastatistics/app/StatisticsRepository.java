package fr.deroffal.stravastatistics.app;

import java.time.Instant;
import java.util.Optional;

public interface StatisticsRepository {

  Optional<Instant> getLastRecordedActivityDate();

  ActivityWithSummary saveActivityWithSummary(ActivityWithSummary activityWithSummary);
}

package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.client.model.DetailedActivity;
import fr.deroffal.stravastatistics.client.model.SummaryActivity;
import java.time.Instant;
import java.util.Optional;

public interface StatisticsRepository {

  Optional<Instant> getLastRecordedActivityDate();

  SummaryActivity saveSummary(SummaryActivity summaryActivity);

  DetailedActivity saveActivity(DetailedActivity detailedActivity);
}

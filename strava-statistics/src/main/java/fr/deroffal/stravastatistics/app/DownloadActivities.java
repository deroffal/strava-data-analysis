package fr.deroffal.stravastatistics.app;

import static java.util.Comparator.comparing;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DownloadActivities {

  private static final Logger LOGGER = LoggerFactory.getLogger(DownloadActivities.class);

  private static final Instant DEFAULT_START_DATE = Instant.parse("2000-01-01T00:00:00.000Z");

  private final StatisticsRepository statisticsRepository;
  private final StravaClient client;

  public DownloadActivities(StatisticsRepository statisticsRepository, StravaClient client) {
    this.statisticsRepository = statisticsRepository;
    this.client = client;
  }

  public void download() {
    Instant lastRecordedActivityDate = statisticsRepository.getLastRecordedActivityDate()
        .orElse(DEFAULT_START_DATE);

    LOGGER.info("Last recorded activity date is {}", lastRecordedActivityDate);

    var activities = client.getActivityWithSummarySince(lastRecordedActivityDate);

    activities.forEach(statisticsRepository::saveActivityWithSummary);

    LOGGER.info("Done with {} activities.", activities.size());

    activities.stream()
        .max(comparing(act -> act.summaryActivity().getStartDate()))
        .ifPresent(last -> LOGGER.info("Last recorded activity date is now {}", last.summaryActivity().getStartDate()));

  }

}

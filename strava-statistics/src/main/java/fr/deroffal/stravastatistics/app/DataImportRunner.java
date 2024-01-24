package fr.deroffal.stravastatistics.app;

import static java.util.Comparator.comparing;

import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataImportRunner implements ApplicationRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataImportRunner.class);

  private static final Instant DEFAULT_START_DATE = Instant.parse("2000-01-01T00:00:00.000Z");

  private final StatisticsRepository statisticsRepository;
  private final StravaClient client;

  public DataImportRunner(StatisticsRepository statisticsRepository, StravaClient client) {
    this.statisticsRepository = statisticsRepository;
    this.client = client;
  }

  @Override
  public void run(ApplicationArguments args) {
    process();
    System.exit(0);
  }

  public void process() {
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

package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.app.StravaClient.ActivityClient;
import fr.deroffal.stravastatistics.app.StravaClient.AthleteClient;
import fr.deroffal.stravastatistics.model.ActivityStats;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.DetailedAthlete;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import fr.deroffal.stravastatistics.model.Zones;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
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

  // just to print on activiy :
//  private void process() {
//    DetailedActivity detailedActivity = client.getDetailedActivity(4120884444L);
//    System.out.println(detailedActivity);
//  }

  private void process() {

    //optional : print athlete statistics
//    printAthleteStatistics();

    Instant lastRecordedActivityDate = statisticsRepository.getLastRecordedActivityDate()
        .orElse(DEFAULT_START_DATE);

    LOGGER.info("Last recorded activity date is {}", lastRecordedActivityDate);

    ActivityClient activityClient = client.getActivityClient();

    Collection<SummaryActivity> summaryActivitiesSince = activityClient.getSummaryActivitiesSince(
        lastRecordedActivityDate);

    LOGGER.debug("{} activities are fetched", summaryActivitiesSince.size());

    List<ActivityWithSummary> list = summaryActivitiesSince.stream()
        .map(summaryActivity -> {
          LOGGER.info("Processing activity {}", summaryActivity.getId());
          DetailedActivity detailedActivity = activityClient.getDetailedActivity(summaryActivity.getId());
          return statisticsRepository.saveActivityWithSummary(
              new ActivityWithSummary(summaryActivity, detailedActivity));
        })
        .toList();

    LOGGER.info("Done with {} activities.", list.size());
  }

  private void printAthleteStatistics() {
    AthleteClient athleteClient = client.getAthleteClient();
    DetailedAthlete detailedAthlete = athleteClient.getDetailedAthlete();
    Zones zones = athleteClient.getZones();
    ActivityStats athleteStats = athleteClient.getAthleteStats(detailedAthlete.getId());
    AthleteSummary athleteSummary = new AthleteSummary(detailedAthlete, zones, athleteStats);
    System.out.println(athleteSummary);
  }

}

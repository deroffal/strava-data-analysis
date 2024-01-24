package fr.deroffal.stravastatistics.client;

import fr.deroffal.stravastatistics.app.ActivityWithSummary;
import fr.deroffal.stravastatistics.app.StravaClient;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class StravaClientImpl implements StravaClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(StravaClientImpl.class);

  private final ActivityClient activityClient;

  StravaClientImpl(ActivityClient activityClient) {
    this.activityClient = activityClient;
  }

  @Override
  public List<ActivityWithSummary> getActivityWithSummarySince(Instant lastRecordedActivityDate) {
    var summaryActivitiesSince = activityClient.getSummaryActivitiesSince(lastRecordedActivityDate);

    LOGGER.debug("{} activities are fetched", summaryActivitiesSince.size());

    return summaryActivitiesSince.stream()
        .map(summaryActivity -> {
          LOGGER.info("Processing activity {}", summaryActivity.getId());
          DetailedActivity detailedActivity = activityClient.getDetailedActivity(summaryActivity.getId());
          return new ActivityWithSummary(summaryActivity, detailedActivity);

        })
        .toList();
  }
}

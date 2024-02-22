package fr.deroffal.stravastatistics.client;

import static java.lang.Math.min;

import fr.deroffal.stravastatistics.app.ActivityWithSummary;
import fr.deroffal.stravastatistics.app.StravaClient;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
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
    var fetchActivitiesResponse = activityClient.getSummaryActivitiesSince(lastRecordedActivityDate);
    var summaryActivitiesSince = fetchActivitiesResponse.activities();


    LOGGER.debug("{} activities are fetched", summaryActivitiesSince.size());

    int remainingCalls = min(fetchActivitiesResponse.quarterHourRate().getRemainingCalls(), fetchActivitiesResponse.dailyRate().getRemainingCalls());

    LOGGER.info("remainingCalls allow : {}", remainingCalls);

    return summaryActivitiesSince.stream()
        .limit(remainingCalls)
        .map(summaryActivity -> {
          LOGGER.info("Processing activity {}", summaryActivity.getId());
          var detailedActivity = activityClient.getDetailedActivity(summaryActivity.getId());
          return new ActivityWithSummary(summaryActivity, detailedActivity);
        })
        .toList();
  }
}

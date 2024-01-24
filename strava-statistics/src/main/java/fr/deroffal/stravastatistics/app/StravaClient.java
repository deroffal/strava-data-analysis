package fr.deroffal.stravastatistics.app;

import java.time.Instant;
import java.util.List;

public interface StravaClient {
  List<ActivityWithSummary> getActivityWithSummarySince(Instant lastRecordedActivityDate);

}

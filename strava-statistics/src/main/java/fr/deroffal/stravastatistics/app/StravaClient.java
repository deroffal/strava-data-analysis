package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.client.model.ActivityStats;
import fr.deroffal.stravastatistics.client.model.DetailedActivity;
import fr.deroffal.stravastatistics.client.model.DetailedAthlete;
import fr.deroffal.stravastatistics.client.model.SummaryActivity;
import fr.deroffal.stravastatistics.client.model.Zones;
import java.time.Instant;
import java.util.Collection;

public interface StravaClient {

  ActivityClient getActivityClient();

  AthleteClient getAthleteClient();

  interface ActivityClient {

    Collection<SummaryActivity> getSummaryActivitiesSince(final Instant instant);

    DetailedActivity getDetailedActivity(final Long activityId);
  }

  interface AthleteClient{

    DetailedAthlete getDetailedAthlete();
    Zones getZones();

    ActivityStats getAthleteStats(long athleteId);

  }
}

package fr.deroffal.stravastatistics.client;

import fr.deroffal.stravastatistics.app.StravaClient;
import org.springframework.stereotype.Service;

@Service
public class StravaClientImpl implements StravaClient {

  private final ActivityClient activityClient;
  private final AthleteClient athleteClient;

  public StravaClientImpl(ActivityClient activityClient, AthleteClient athleteClient) {
    this.activityClient = activityClient;
    this.athleteClient = athleteClient;
  }

  @Override
  public ActivityClient getActivityClient() {
    return activityClient;
  }

  @Override
  public AthleteClient getAthleteClient() {
    return athleteClient;
  }
}

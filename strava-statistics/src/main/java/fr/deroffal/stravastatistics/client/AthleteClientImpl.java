package fr.deroffal.stravastatistics.client;

import fr.deroffal.stravastatistics.app.StravaClient.AthleteClient;
import fr.deroffal.stravastatistics.model.ActivityStats;
import fr.deroffal.stravastatistics.model.DetailedAthlete;
import fr.deroffal.stravastatistics.model.Zones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AthleteClientImpl implements AthleteClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(AthleteClientImpl.class);

  private final RestClient restClient;

  public AthleteClientImpl(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public DetailedAthlete getDetailedAthlete() {
    LOGGER.debug("getDetailedAthlete");
    return restClient.get()
        .uri(builder -> builder.path("athlete").build())
        .retrieve().body(DetailedAthlete.class);
  }

  @Override
  public Zones getZones() {
    LOGGER.debug("getZones");
    return restClient.get()
        .uri(builder -> builder.path("athlete/zones").build())
        .retrieve().body(Zones.class);
  }

  @Override
  public ActivityStats getAthleteStats(long athleteId) {
    LOGGER.debug("getAthleteStats - {}", athleteId);
    return restClient.get()
        .uri(builder -> builder.path("/athlete/%s/stats".formatted(athleteId)).build())
        .retrieve().body(ActivityStats.class);
  }
}

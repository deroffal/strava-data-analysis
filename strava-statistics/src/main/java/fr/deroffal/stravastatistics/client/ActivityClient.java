package fr.deroffal.stravastatistics.client;


import fr.deroffal.stravastatistics.client.rate.RateAdapter;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import java.time.Instant;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
class ActivityClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityClient.class);

  private final RestClient restClient;
  private final StravaApiConfiguration stravaApiConfiguration;

  public ActivityClient(RestClient restClient, StravaApiConfiguration stravaApiConfiguration) {
    this.restClient = restClient;
    this.stravaApiConfiguration = stravaApiConfiguration;
  }

  public FetchActivitiesResponse getSummaryActivitiesSince(final Instant instant) {
    var entity = restClient.get()
        .uri(builder -> builder.path("athlete/activities")
            .queryParam("per_page", stravaApiConfiguration.getSettings().getActivityPerPage())
            .queryParam("after", instant.getEpochSecond())
            .build()
        )
        .retrieve()
        .toEntity(new ParameterizedTypeReference<Collection<SummaryActivity>>() {});

    var rateAdapter = new RateAdapter(entity.getHeaders());
    return new FetchActivitiesResponse(entity.getBody(), rateAdapter.getQuarterHourRate(), rateAdapter.getDailyRate());
  }

  public DetailedActivity getDetailedActivity(Long activityId) {
    LOGGER.debug("getDetailedActivity - {}", activityId);
    return restClient.get()
        .uri(builder -> builder.path("activities/" + activityId).build())
        .retrieve().body(DetailedActivity.class);
  }


}

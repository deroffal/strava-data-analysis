package fr.deroffal.stravastatistics.client;


import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.deroffal.stravastatistics.client.rate.RateAdapter;
import fr.deroffal.stravastatistics.client.rate.RateExceededException;
import fr.deroffal.stravastatistics.model.CustomDetailedActivity;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse;

@Service
class ActivityClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityClient.class);

  private static final int RATE_EXCEEDED_STATUS_CODE = 429;

  private final RestClient restClient;
  private final StravaApiConfiguration stravaApiConfiguration;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public ActivityClient(RestClient restClient, StravaApiConfiguration stravaApiConfiguration) {
    this.restClient = restClient;
    this.stravaApiConfiguration = stravaApiConfiguration;
  }

  public FetchActivitiesResponse getSummaryActivitiesSince(final Instant instant) {
    return restClient.get()
        .uri(builder -> builder.path("athlete/activities")
            .queryParam("per_page", stravaApiConfiguration.getSettings().getActivityPerPage())
            .queryParam("after", instant.getEpochSecond())
            .build()
        )
        .exchange((request, response) -> {
          if (response.getStatusCode().is2xxSuccessful()) {
            return toFetchActivitiesResponse(response);
          } else if (response.getStatusCode().value() == 429) {
            throw new RateExceededException(response.bodyTo(String.class));
          }
          throw new RuntimeException("Error %s while fetching athlete activities ! Body is %s".formatted(
              response.getStatusCode(),
              response.bodyTo(String.class)));
        });

  }

  private FetchActivitiesResponse toFetchActivitiesResponse(ConvertibleClientHttpResponse response)
      throws IOException {
    var summaryActivities = Optional.ofNullable(
            response.bodyTo(new ParameterizedTypeReference<Collection<SummaryActivity>>() {}))
        .orElse(List.of());

    JsonNode jsonResponse = objectMapper.readTree(response.getBody());

    List<CustomSummaryActivity> activities = summaryActivities.stream()
        .map(activity -> {
          Long id = activity.getId();
          JsonNode activityNode = StreamSupport.stream(jsonResponse.spliterator(), false)
              .filter(it -> id == it.get("id").longValue())
              .findFirst().orElseThrow(() -> new IllegalArgumentException("Can't find json activity with id : " + id));
          return new CustomSummaryActivity(activity, activityNode.toString());
        })
        .toList();

    var rateAdapter = new RateAdapter(response.getHeaders());

    return new FetchActivitiesResponse(activities, rateAdapter.getQuarterHourRate(),
        rateAdapter.getDailyRate());
  }

  public CustomDetailedActivity getDetailedActivity(Long activityId) {
    LOGGER.debug("getDetailedActivity - {}", activityId);
    return restClient.get()
        .uri(builder -> builder.path("activities/" + activityId).build())
        .exchange((request, response) -> {
          if (response.getStatusCode().is2xxSuccessful()) {
            DetailedActivity detailedActivity = response.bodyTo(DetailedActivity.class);
            String json = new BufferedReader(new InputStreamReader(response.getBody())).lines().collect(joining(""));
            return new CustomDetailedActivity(detailedActivity, json);
          } else if (response.getStatusCode().value() == RATE_EXCEEDED_STATUS_CODE) {
            throw new RateExceededException(response.bodyTo(String.class));
          }
          throw new RuntimeException("Error %s while fetching activity %s ! Body is %s".formatted(
              response.getStatusCode(),
              activityId,
              response.bodyTo(String.class)));
        });
  }


}

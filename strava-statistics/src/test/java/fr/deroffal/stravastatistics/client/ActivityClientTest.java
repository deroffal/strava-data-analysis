package fr.deroffal.stravastatistics.client;

import static fr.deroffal.stravastatistics.Utils.getFileContent;
import static fr.deroffal.stravastatistics.client.AccessTokenProvider.PROFILE_MOCK_TOKEN_PROVIDER;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON;

import fr.deroffal.stravastatistics.model.CustomDetailedActivity;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.DetailedSegmentEffort;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@MockServerTest("strava-statistics.client.api.url=http://localhost:${mockServerPort}")
@ActiveProfiles({"client", PROFILE_MOCK_TOKEN_PROVIDER})
@SpringBootTest(classes = ClientTestConfiguration.class)
class ActivityClientTest {

  @Autowired
  private ActivityClient activityClient;

  private MockServerClient client;

  @Test
  void getSummaryActivitiesSince() {
    String responseBody = getFileContent("summaryActivities.json");

    client.when(
            request()
                .withMethod("GET")
                .withPath("/athlete/activities")
                .withHeader("Authorization", "Bearer mock-accessToken")
                .withQueryStringParameter("per_page", "49")
                .withQueryStringParameter("after", "1644710400")
        )
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("x-ratelimit-usage", "103,160"),
                    new Header("x-ratelimit-limit", "100,1000")
                )
                .withContentType(APPLICATION_JSON)
                .withBody(responseBody)
        );

    Collection<CustomSummaryActivity> result = activityClient.getSummaryActivitiesSince(
        Instant.parse("2022-02-13T00:00:00Z")).activities();

    List<SummaryActivity> activities = result.stream().map(CustomSummaryActivity::getObject).toList();

    assertThat(result.stream().map(CustomSummaryActivity::getPayload)).allMatch(responseBody::contains);

    assertThat(activities.stream().map(SummaryActivity::getId))
        .containsExactlyInAnyOrder(6465654440L, 6497819499L, 6516576106L, 6553064943L, 6570536821L, 6580524741L,
            6601021180L, 6624657683L, 6646185382L, 6670471673L);
  }

  @Test
  void getSummaryActivitiesSince_withNoActivity() {
    client.when(
            request()
                .withMethod("GET")
                .withPath("/athlete/activities")
                .withHeader("Authorization", "Bearer mock-accessToken")
                .withQueryStringParameter("per_page", "49")
                .withQueryStringParameter("after", "1644710400")
        )
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("x-ratelimit-usage", "103,160"),
                    new Header("x-ratelimit-limit", "100,1000")
                )
                .withContentType(APPLICATION_JSON)
                .withBody("[]")
        );

    Collection<CustomSummaryActivity> result = activityClient.getSummaryActivitiesSince(
        Instant.parse("2022-02-13T00:00:00Z")).activities();

    assertThat(result).isEmpty();
  }

  @Test
  void getDetailedActivity() {
    String responseBody = getFileContent("activity.json");

    client.when(
            request()
                .withMethod("GET")
                .withPath("/activities/7454700738")
        )
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("x-ratelimit-usage", "103,160"),
                    new Header("x-ratelimit-limit", "100,1000")
                )
                .withContentType(APPLICATION_JSON)
                .withBody(responseBody)
        );

    CustomDetailedActivity result = activityClient.getDetailedActivity(7454700738L);

    assertThat(result.getPayload()).isEqualToNormalizingWhitespace(responseBody.lines().collect(joining("")));

    DetailedActivity detailedActivity = result.getObject();
    assertThat(detailedActivity.getId()).isEqualTo(7454700738L);
  }

  @Test
  @DisplayName("test that we are able to avoid error while deserializing Hiking Activity Type ")
  void getDetailedActivity_hikeError() {
    String responseBody = getFileContent("activity-hike.json");

    client.when(
            request()
                .withMethod("GET")
                .withPath("/activities/4120884444")
                .withHeader("Authorization", "Bearer mock-accessToken")
        )
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("x-ratelimit-usage", "103,160"),
                    new Header("x-ratelimit-limit", "100,1000")
                )
                .withContentType(APPLICATION_JSON)
                .withBody(responseBody)
        );

    CustomDetailedActivity result = activityClient.getDetailedActivity(4120884444L);
    DetailedActivity detailedActivity = result.getObject();

    assertThat(result.getPayload()).isEqualToNormalizingWhitespace(responseBody);

    assertThat(detailedActivity.getId()).isEqualTo(4120884444L);

    DetailedSegmentEffort segmentEffort = detailedActivity.getSegmentEfforts().getFirst();
    assertThat(segmentEffort.getSegment().getActivityType()).isNull();
  }


}

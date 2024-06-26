package fr.deroffal.stravastatistics.client;

import static fr.deroffal.stravastatistics.Utils.getFileContent;
import static fr.deroffal.stravastatistics.client.AccessTokenProvider.PROFILE_MOCK_TOKEN_PROVIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON;

import fr.deroffal.stravastatistics.model.ActivityStats;
import fr.deroffal.stravastatistics.model.DetailedAthlete;
import fr.deroffal.stravastatistics.model.SummaryGear;
import fr.deroffal.stravastatistics.model.Zones;
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
class AthleteClientTest {

  @Autowired
  private AthleteClient athleteClient;

  private MockServerClient client;


  @Test
  void getDetailedAthlete() {
    client.when(
            request()
                .withMethod("GET")
                .withPath("/athlete")
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
                .withBody(getFileContent("athlete.json"))
        );

    DetailedAthlete athlete = athleteClient.getDetailedAthlete();

    assertThat(athlete.getId()).isEqualTo(42223740L);
    assertThat(athlete.getClubs()).hasSize(2);
    assertThat(athlete.getShoes().stream().map(SummaryGear::getName)).containsExactlyInAnyOrder("New Balance 840",
        "Kiprun KS900");
  }

  @Test
  void getZones() {
    client.when(
            request()
                .withMethod("GET")
                .withPath("/athlete/zones")
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
                .withBody(getFileContent("zones.json"))
        );

    Zones zones = athleteClient.getZones();

    assertThat(zones.getHeartRate().getZones()).hasSize(5);

  }

  @Test
  void getAthleteStats() {
    client.when(
            request()
                .withMethod("GET")
                .withPath("/athlete/42223740/stats")
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
                .withBody(getFileContent("stats.json"))
        );

    ActivityStats athleteStats = athleteClient.getAthleteStats(42223740L);

    assertThat(athleteStats.getAllRideTotals().getCount()).isEqualTo(74);
    assertThat(athleteStats.getAllRunTotals().getCount()).isEqualTo(297);
    assertThat(athleteStats.getAllSwimTotals().getCount()).isEqualTo(0);
  }
}

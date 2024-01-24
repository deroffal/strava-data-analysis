package fr.deroffal.stravastatistics.client.auth;

import static fr.deroffal.stravastatistics.Utils.getFileContent;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.MediaType.APPLICATION_JSON;

import fr.deroffal.stravastatistics.client.StravaApiConfiguration;
import fr.deroffal.stravastatistics.client.StravaApiConfiguration.ApiConfig;
import java.net.MalformedURLException;
import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@MockServerTest("strava-statistics.client.api.url=http://localhost:${mockServerPort}")
@ExtendWith(SpringExtension.class)
class StravaAuthApiTest {

  @InjectMocks
  private StravaAuthApi cut;
  @Mock
  private StravaApiConfiguration stravaApiConfiguration;

  @Value("${strava-statistics.client.api.url}")
  private String mockServerUrl;
  private MockServerClient client;

  @BeforeEach
  void setup() throws MalformedURLException {
    var apiConfig = new ApiConfig();
    apiConfig.setUrl(URI.create(mockServerUrl).toURL());
    apiConfig.setClientId("12345");
    apiConfig.setClientSecret("8cf3ae6da95dda35632abc4ce34ade9fa78de0e");
    apiConfig.setRefreshToken("3a09c939a88b7a88a26a7d05d6b44437f9eb2c0");
    when(stravaApiConfiguration.getApi()).thenReturn(apiConfig);
  }

  @Test
  void getAccessToken() {
    //check auth call
    client.when(
            request()
                .withMethod("POST")
                .withPath("/oauth/token")
                .withHeaders(
                    new Header("Accept", "application/json")
                )
                .withQueryStringParameter("client_id", "12345")
                .withQueryStringParameter("client_secret", "8cf3ae6da95dda35632abc4ce34ade9fa78de0e")
                .withQueryStringParameter("grant_type", "refresh_token")
                .withQueryStringParameter("refresh_token", "3a09c939a88b7a88a26a7d05d6b44437f9eb2c0")
        )
        .respond(
            response()
                .withStatusCode(200)
                .withContentType(APPLICATION_JSON)
                .withHeaders(
                    new Header("x-ratelimit-usage", "103,160"),
                    new Header("x-ratelimit-limit", "100,1000")
                )
                .withContentType(APPLICATION_JSON)
                .withBody(getFileContent("refresh-ok.json"))
        );

    String accessToken = cut.getAccessToken();

    Assertions.assertThat(accessToken).isEqualTo("10f2fcbff5a0a09c939a88b7a88a2ea5533186fb");

  }


}

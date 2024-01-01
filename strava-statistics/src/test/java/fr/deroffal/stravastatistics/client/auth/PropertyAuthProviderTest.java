package fr.deroffal.stravastatistics.client.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import fr.deroffal.stravastatistics.client.StravaApiConfiguration;
import fr.deroffal.stravastatistics.client.StravaApiConfiguration.ApiConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyAuthProviderTest {

  @InjectMocks
  private PropertyAuthProvider cut;

  @Mock
  private StravaApiConfiguration stravaApiConfiguration;

  @Test
  void getAccessToken() {
    var apiConfig = new ApiConfig();
    apiConfig.setAccessToken("8cf3ae6da95dda35632abc4ce34ade9fa78de0e");
    when(stravaApiConfiguration.getApi()).thenReturn(apiConfig);

    assertThat(cut.getAccessToken()).isEqualTo("8cf3ae6da95dda35632abc4ce34ade9fa78de0e");
  }

  @Test
  void afterPropertiesSet_doesNothing_whenAccessTokenIsSet() {
    var apiConfig = new ApiConfig();
    apiConfig.setAccessToken("8cf3ae6da95dda35632abc4ce34ade9fa78de0e");
    when(stravaApiConfiguration.getApi()).thenReturn(apiConfig);

    assertDoesNotThrow(()-> cut.afterPropertiesSet());
  }

  @Test
  void afterPropertiesSet_throwsException_whenAccessTokenIsNotSet() {
    var apiConfig = new ApiConfig();
    when(stravaApiConfiguration.getApi()).thenReturn(apiConfig);

    assertThatThrownBy(()-> cut.afterPropertiesSet())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("With this profile, property 'strava-statistics.client.api.accessToken' must not be null !");
  }


}

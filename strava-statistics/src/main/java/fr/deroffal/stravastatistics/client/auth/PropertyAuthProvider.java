package fr.deroffal.stravastatistics.client.auth;

import static fr.deroffal.stravastatistics.client.AccessTokenProvider.PROFILE_PROPERTY_TOKEN_PROVIDER;

import fr.deroffal.stravastatistics.client.AccessTokenProvider;
import fr.deroffal.stravastatistics.client.StravaApiConfiguration;
import java.util.Objects;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(PROFILE_PROPERTY_TOKEN_PROVIDER)
class PropertyAuthProvider implements AccessTokenProvider, InitializingBean {

  private final StravaApiConfiguration stravaApiConfiguration;

  public PropertyAuthProvider(StravaApiConfiguration stravaApiConfiguration) {
    this.stravaApiConfiguration = stravaApiConfiguration;
  }

  @Override
  public String getAccessToken() {
    return stravaApiConfiguration.getApi().getAccessToken();
  }

  @Override
  public void afterPropertiesSet() {
    Objects.requireNonNull(stravaApiConfiguration.getApi().getAccessToken(), "With this profile, property 'strava-statistics.client.api.accessToken' must not be null !");
  }
}

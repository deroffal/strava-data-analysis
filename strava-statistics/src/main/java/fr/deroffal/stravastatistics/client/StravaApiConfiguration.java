package fr.deroffal.stravastatistics.client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "strava-statistics.client")
public class StravaApiConfiguration {

  private ApiConfig api;
  private SettingsConfig settings;

  public ApiConfig getApi() {
    return api;
  }

  public void setApi(ApiConfig api) {
    this.api = api;
  }

  public SettingsConfig getSettings() {
    return settings;
  }

  public void setSettings(SettingsConfig settings) {
    this.settings = settings;
  }

  public static class ApiConfig {

    @NotNull
    private URL url;
    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;
    @NotBlank
    private String refreshToken;
    @Nullable
    private String accessToken;

    public URL getUrl() {
      return url;
    }

    public void setUrl(URL url) {
      this.url = url;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }

    public String getClientSecret() {
      return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
      this.clientSecret = clientSecret;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }

    @Nullable
    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(@Nullable String accessToken) {
      this.accessToken = accessToken;
    }
  }

  public static class SettingsConfig {

    @Min(1)
    @Max(99)
    private int activityPerPage;

    public int getActivityPerPage() {
      return activityPerPage;
    }

    public void setActivityPerPage(int activityPerPage) {
      this.activityPerPage = activityPerPage;
    }
  }
}

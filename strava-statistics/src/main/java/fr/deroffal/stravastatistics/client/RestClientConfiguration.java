package fr.deroffal.stravastatistics.client;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

  private final AccessTokenProvider accessTokenProvider;
  private final StravaApiConfiguration stravaApiConfiguration;

  public RestClientConfiguration(AccessTokenProvider accessTokenProvider, StravaApiConfiguration stravaApiConfiguration) {
    this.accessTokenProvider = accessTokenProvider;
    this.stravaApiConfiguration = stravaApiConfiguration;
  }

  @Bean
  @Lazy
  public RestClient stravaRestClient(){
    String baseUrl = stravaApiConfiguration.getApi().getUrl().toString();

    return RestClient.builder()
        .messageConverters(RestClientConfiguration::configureDefaultObjectMapper)
        .baseUrl(baseUrl)
        .defaultHeader("Authorization", "Bearer " + accessTokenProvider.getAccessToken())
        .defaultHeader("Accept", "application/json")
        .requestInterceptor(new RateLoggerInterceptor())
        .build();
  }


  private static void configureDefaultObjectMapper(List<HttpMessageConverter<?>> messageConverters) {
    messageConverters.stream()
        .filter(it -> it.getClass().equals(MappingJackson2HttpMessageConverter.class))
        .map(it -> ((MappingJackson2HttpMessageConverter) it).getObjectMapper())
        .forEach(RestClientConfiguration::handleUnknwownEnumValue);

  }

  //https://communityhub.strava.com/t5/developer-discussions/api-summarysegment-documentation/m-p/10676/thread-id/451
  private static void handleUnknwownEnumValue(ObjectMapper objectMapper) {
    objectMapper.configure(READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
  }
}

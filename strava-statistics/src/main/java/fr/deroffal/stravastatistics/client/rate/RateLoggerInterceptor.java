package fr.deroffal.stravastatistics.client.rate;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class RateLoggerInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(RateLoggerInterceptor.class);

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
      throws IOException {

    ClientHttpResponse response = execution.execute(request, body);
    RateAdapter rateAdapter = new RateAdapter(response.getHeaders());

    LOGGER.atDebug().log("15min rate : {}", rateAdapter.getQuarterHourRate());
    LOGGER.atDebug().log("daily rate : {}", rateAdapter.getDailyRate());

    return response;

  }
}

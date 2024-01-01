package fr.deroffal.stravastatistics.client;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class RateLoggerInterceptor implements ClientHttpRequestInterceptor{

  private static final Logger LOGGER = LoggerFactory.getLogger(RateLoggerInterceptor.class);

  /**
   *      //17:14:35.986 [main] DEBUG f.d.s.client.RateLoggerInterceptor - x-ratelimit-usage : 103,160
   *      //17:14:36.881 [main] DEBUG f.d.s.client.RateLoggerInterceptor - x-ratelimit-limit : 100,1000
   */
  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
      throws IOException {

     ClientHttpResponse response = execution.execute(request, body);
     HttpHeaders headers = response.getHeaders();
     String usage = headers.get("x-ratelimit-usage").getFirst();
     String limit = headers.get("x-ratelimit-limit").getFirst();

    String[] usages = usage.split(",");
    String[] limits = limit.split(",");

    LOGGER.atDebug().log("15min rate : {}/{}", usages[0], limits[0]);
    LOGGER.atDebug().log("daily rate : {}/{}", usages[1], limits[1]);

     return response;

  }
}

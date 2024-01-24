package fr.deroffal.stravastatistics.client.rate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * //17:14:35.986 [main] DEBUG f.d.s.client.RateLoggerInterceptor - x-ratelimit-usage : 103,160
 * //17:14:36.881 [main] DEBUG f.d.s.client.RateLoggerInterceptor - x-ratelimit-limit : 100,1000
 */
class RateAdapterTest {

  @Test
  void getQuarterHourRate() {
    String usage = "103,160";
    String limite = "100,1000";

    RateAdapter rateAdapter = new RateAdapter(usage, limite);

    ApiRate quarterHourRate = rateAdapter.getQuarterHourRate();
    assertThat(quarterHourRate.usage()).isEqualTo(103);
    assertThat(quarterHourRate.limit()).isEqualTo(100);
  }

  @Test
  void getDailyRate() {
    var headers = new HttpHeaders();
    headers.add( "x-ratelimit-usage","103,160");
    headers.add( "x-ratelimit-limit","100,1000");

    RateAdapter rateAdapter = new RateAdapter(headers);

    ApiRate dailyRate = rateAdapter.getDailyRate();
    assertThat(dailyRate.usage()).isEqualTo(160);
    assertThat(dailyRate.limit()).isEqualTo(1000);
  }

}

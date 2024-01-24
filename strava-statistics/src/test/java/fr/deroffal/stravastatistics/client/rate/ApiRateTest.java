package fr.deroffal.stravastatistics.client.rate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ApiRateTest {

  @Test
  void getRemainingCalls_whenRateNotReached(){
    ApiRate apiRate = new ApiRate(10, 100);
    assertThat(apiRate.getRemainingCalls()).isEqualTo(90);
  }

  @Test
  void getRemainingCalls_whenRateReached(){
    ApiRate apiRate = new ApiRate(103, 100);
    assertThat(apiRate.getRemainingCalls()).isEqualTo(0);
  }

  @Test
  void apiRate_toString(){
    ApiRate apiRate = new ApiRate(10, 100);
    assertThat(apiRate.toString()).isEqualTo("10/100");
  }
}

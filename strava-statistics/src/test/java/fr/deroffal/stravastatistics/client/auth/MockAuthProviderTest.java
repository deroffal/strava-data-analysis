package fr.deroffal.stravastatistics.client.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MockAuthProviderTest {

  private final MockAuthProvider cut = new MockAuthProvider();

  @Test
  void getAccessToken() {
    assertThat(cut.getAccessToken()).isEqualTo("mock-accessToken");
  }

}

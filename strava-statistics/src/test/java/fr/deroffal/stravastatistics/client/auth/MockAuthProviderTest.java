package fr.deroffal.stravastatistics.client.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

class MockAuthProviderTest {

  private final MockAuthProvider cut = new MockAuthProvider();

  @Test
  void getAccessToken() {
    assertThat(cut.getAccessToken()).isEqualTo("mock-accessToken");
  }

}

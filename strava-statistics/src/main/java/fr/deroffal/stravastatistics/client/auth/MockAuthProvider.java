package fr.deroffal.stravastatistics.client.auth;

import static fr.deroffal.stravastatistics.client.AccessTokenProvider.PROFILE_MOCK_TOKEN_PROVIDER;

import fr.deroffal.stravastatistics.client.AccessTokenProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(PROFILE_MOCK_TOKEN_PROVIDER)
class MockAuthProvider implements AccessTokenProvider {

  @Override
  public String getAccessToken() {
    return "mock-accessToken";
  }
}

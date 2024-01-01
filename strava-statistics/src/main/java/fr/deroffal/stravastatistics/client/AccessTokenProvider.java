package fr.deroffal.stravastatistics.client;

public interface AccessTokenProvider {

  String PROFILE_API_TOKEN_PROVIDER = "apiTokenProvider";
  String PROFILE_PROPERTY_TOKEN_PROVIDER = "propertyTokenProvider";
  String PROFILE_MOCK_TOKEN_PROVIDER = "mockTokenProvider";

  String getAccessToken();
}

package fr.deroffal.stravastatistics.repository;

import static com.mongodb.ServerApiVersion.V1;
import static fr.deroffal.stravastatistics.repository.CustomConverters.dateZonedDateTimeConverter;
import static fr.deroffal.stravastatistics.repository.CustomConverters.zonedDateTimeDateConverter;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.connection.ConnectionPoolSettings;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "strava-statistics.mongo")
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {

  public static final String SUMMARY_ACTIVITY_COLLECTION = "strava_summary_activity";
  public static final String DETAILED_ACTIVITY_COLLECTION = "strava_detailed_activity";
  public static final String SUMMARY_ACTIVITY_JAVA_COLLECTION = "strava_summary_activity_java";
  public static final String DETAILED_ACTIVITY_JAVA_COLLECTION = "strava_detailed_activity_java";

  @NotBlank
  private String database;

  @NotBlank
  private String url;

  @Override
  protected String getDatabaseName() {
    return database;
  }

  @Override
  protected MongoClientSettings mongoClientSettings() {
    ConnectionString connectionString = new ConnectionString(getUrl());

    ServerApi serverApi = ServerApi.builder()
        .version(V1)
        .build();

    return MongoClientSettings.builder()
        .serverApi(serverApi)
        .applyConnectionString(connectionString)
        .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> builder.maxWaitTime(5, SECONDS))
        .applyToSocketSettings(builder -> builder.connectTimeout(5, SECONDS))
        .build();
  }

  @Override
  protected FieldNamingStrategy fieldNamingStrategy() {
    return new SnakeCaseFieldNamingStrategy();
  }

  @Override
  protected void configureConverters(MongoConverterConfigurationAdapter converterConfigurationAdapter) {
    converterConfigurationAdapter
        .registerConverter(dateZonedDateTimeConverter())
        .registerConverter(zonedDateTimeDateConverter());
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}



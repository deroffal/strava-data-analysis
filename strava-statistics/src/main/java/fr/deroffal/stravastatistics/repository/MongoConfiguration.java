package fr.deroffal.stravastatistics.repository;

import static fr.deroffal.stravastatistics.repository.CustomConverters.dateZonedDateTimeConverter;
import static fr.deroffal.stravastatistics.repository.CustomConverters.zonedDateTimeDateConverter;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "strava-statistics.mongo")
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {

  @NotBlank
  private String database;

  @NotBlank
  private String url;

  @Override
  protected String getDatabaseName() {
    return database;
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(getUrl());
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Override
  public Collection getMappingBasePackages() {
    return Collections.singleton(this.getClass().getPackage().getName());
  }

  protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
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

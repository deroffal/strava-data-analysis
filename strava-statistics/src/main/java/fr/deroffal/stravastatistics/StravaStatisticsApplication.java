package fr.deroffal.stravastatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class StravaStatisticsApplication {

  public static void main(final String[] args) {
    SpringApplication.run(StravaStatisticsApplication.class, args);
  }

}

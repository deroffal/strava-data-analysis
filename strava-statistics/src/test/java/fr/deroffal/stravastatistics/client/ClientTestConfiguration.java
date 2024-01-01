package fr.deroffal.stravastatistics.client;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = StravaApiConfiguration.class)
@ConfigurationPropertiesScan(basePackageClasses = StravaApiConfiguration.class)
public class ClientTestConfiguration {

}

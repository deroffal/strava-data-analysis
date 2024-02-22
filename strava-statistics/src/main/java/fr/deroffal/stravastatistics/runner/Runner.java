package fr.deroffal.stravastatistics.runner;

import fr.deroffal.stravastatistics.app.DownloadActivities;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

  private final DownloadActivities downloadActivities;

  public Runner(DownloadActivities downloadActivities) {
    this.downloadActivities = downloadActivities;
  }

  @Override
  public void run(ApplicationArguments args) {
    downloadActivities.download();
    System.exit(0);
  }
}

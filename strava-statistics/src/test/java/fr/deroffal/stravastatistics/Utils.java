package fr.deroffal.stravastatistics;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

  public static String getFileContent(final String filename) {
    try {
      final URL resource = Utils.class.getClassLoader()
          .getResource("client/" + filename);
      return Files.readString(Paths.get(resource.toURI()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

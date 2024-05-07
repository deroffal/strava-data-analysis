package fr.deroffal.stravastatistics.client.rate;

public class RateExceededException extends RuntimeException{

  public RateExceededException(String message) {
    super(message);
  }
}

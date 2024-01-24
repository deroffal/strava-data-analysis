package fr.deroffal.stravastatistics.client.rate;

import static java.lang.Integer.parseInt;

import org.springframework.util.MultiValueMap;

public record RateAdapter(String rateUsage, String rateLimit) {

  private static final String RATE_DELIMITER = ",";
  private static final int QUARTER_HOUR_POSITION = 0;
  private static final int DAILY_POSITION = 1;
  private static final String RATE_USAGE_HEADER_NAME = "x-ratelimit-usage";
  private static final String RATE_LIMIT_HEADER_NAME = "x-ratelimit-limit";

  public RateAdapter(MultiValueMap<String, String> headers) {
    this(headers.get(RATE_USAGE_HEADER_NAME).getFirst(), headers.get(RATE_LIMIT_HEADER_NAME).getFirst());
  }

  public ApiRate getQuarterHourRate() {
    String[] usages = rateUsage.split(RATE_DELIMITER);
    String[] limits = rateLimit.split(RATE_DELIMITER);
    return new ApiRate(parseInt(usages[QUARTER_HOUR_POSITION]), parseInt(limits[QUARTER_HOUR_POSITION]));
  }

  public ApiRate getDailyRate() {
    String[] usages = rateUsage.split(RATE_DELIMITER);
    String[] limits = rateLimit.split(RATE_DELIMITER);
    return new ApiRate(parseInt(usages[DAILY_POSITION]), parseInt(limits[DAILY_POSITION]));
  }
}

package fr.deroffal.stravastatistics.client.rate;

import static java.lang.Math.max;

public record ApiRate(int usage, int limit) {

  public int getRemainingCalls(){
    return max(0, limit - usage);
  }

  @Override
  public String toString() {
    return usage + "/" + limit;
  }
}

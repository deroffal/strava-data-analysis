package fr.deroffal.stravastatistics.model;

import java.time.OffsetDateTime;

public class CustomSummaryActivity extends ObjectWithPayload<SummaryActivity> {

  public CustomSummaryActivity(SummaryActivity object, String payload) {
    super(object, payload);
  }

  @Override
  public Long getId() {
    return getObject().getId();
  }

  public OffsetDateTime getStartDate() {
    return getObject().getStartDate();
  }
}

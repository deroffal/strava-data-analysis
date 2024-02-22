package fr.deroffal.stravastatistics.model;

public class CustomDetailedActivity extends ObjectWithPayload<DetailedActivity> {

  public CustomDetailedActivity(DetailedActivity object, String payload) {
    super(object, payload);
  }

  @Override
  public Long getId() {
    return getObject().getId();
  }


}

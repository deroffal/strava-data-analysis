package fr.deroffal.stravastatistics.model;

abstract class ObjectWithPayload<T> {

  private final T object;
  private final String payload;

  public ObjectWithPayload(T object, String payload) {
    this.object = object;
    this.payload = payload;
  }

  public T getObject() {
    return object;
  }

  public String getPayload() {
    return payload;
  }

  public abstract Long getId();
}

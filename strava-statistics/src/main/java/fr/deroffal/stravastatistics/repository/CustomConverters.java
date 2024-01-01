package fr.deroffal.stravastatistics.repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public  final class CustomConverters {

  private CustomConverters() {
    throw new IllegalArgumentException();
  }

  public static Converter<Date, OffsetDateTime> dateZonedDateTimeConverter() {
    return new Converter<>() {
      @Override
      public OffsetDateTime convert(Date source) {
        return source.toInstant().atZone(ZoneOffset.UTC).toOffsetDateTime();
      }
    };
  }

  public static Converter<OffsetDateTime, Date> zonedDateTimeDateConverter(){
    return new Converter<>() {
      @Override
      public Date convert(OffsetDateTime source) {
        return Date.from(source.toInstant());
      }
    };
  }

}

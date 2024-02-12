package fr.deroffal.stravastatistics.repository;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface MongoMapper {

  DetailedActivityDocument from(DetailedActivity detailedActivity);
  DetailedActivity from(DetailedActivityDocument activity);

  SummaryActivityDocument from(SummaryActivity summaryActivity);
  SummaryActivity from(SummaryActivityDocument athleteActivity);
}

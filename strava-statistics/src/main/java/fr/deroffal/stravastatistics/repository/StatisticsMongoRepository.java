package fr.deroffal.stravastatistics.repository;

import static fr.deroffal.stravastatistics.repository.MongoConfiguration.DETAILED_ACTIVITY_COLLECTION;
import static fr.deroffal.stravastatistics.repository.MongoConfiguration.SUMMARY_ACTIVITY_COLLECTION;
import static org.springframework.data.domain.Sort.Direction.DESC;

import fr.deroffal.stravastatistics.app.ActivityWithSummary;
import fr.deroffal.stravastatistics.app.StatisticsRepository;
import fr.deroffal.stravastatistics.model.CustomDetailedActivity;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class StatisticsMongoRepository implements StatisticsRepository {

  private final MongoTemplate mongoTemplate;
  private final ActivityRepository activityRepository;
  private final SummaryRepository summaryRepository;
  private final MongoMapper mongoMapper;

  public StatisticsMongoRepository(MongoTemplate mongoTemplate, ActivityRepository activityRepository,
      SummaryRepository summaryRepository, MongoMapper mongoMapper) {
    this.mongoTemplate = mongoTemplate;
    this.activityRepository = activityRepository;
    this.summaryRepository = summaryRepository;
    this.mongoMapper = mongoMapper;
  }

  @Override
  public Optional<Instant> getLastRecordedActivityDate() {
    Query query = new Query();
    query.with(Sort.by(DESC, "start_date")).limit(1);
    Document lastDocument = mongoTemplate.findOne(query, Document.class, SUMMARY_ACTIVITY_COLLECTION);
    return Optional.ofNullable(lastDocument)
        .map(document -> document.getDate("start_date"))
        .map(Date::toInstant);
  }

  @Override
  public void saveActivityWithSummary(ActivityWithSummary activityWithSummary) {
    CustomSummaryActivity customSummaryActivity = activityWithSummary.summaryActivity();
    saveSummary(customSummaryActivity.getPayload());
    SummaryActivityDocument summaryActivityDocument = mongoMapper.from(customSummaryActivity.getObject());
    summaryRepository.save(summaryActivityDocument);
    CustomDetailedActivity customDetailedActivity = activityWithSummary.detailedActivity();
    saveActivity(customDetailedActivity.getPayload());
    DetailedActivityDocument detailedActivityDocument = mongoMapper.from(customDetailedActivity.getObject());
    activityRepository.save(detailedActivityDocument);
  }

  private void saveSummary(String summaryActivity) {
    Document doc = Document.parse(summaryActivity);
    convertDateFields(doc);
    mongoTemplate.insert(doc, SUMMARY_ACTIVITY_COLLECTION);
  }

  private void saveActivity(String detailedActivity) {
    Document doc = Document.parse(detailedActivity);
    convertDateFields(doc);
    mongoTemplate.insert(doc, DETAILED_ACTIVITY_COLLECTION);
  }

  // allow to store date data as Date in Mongo instead of plain String
  private static void convertDateFields(Document doc) {
    doc.keySet().stream()
        .filter(StatisticsMongoRepository::isDateField)
        .forEach(dateField -> convertDateField(doc, dateField));
  }

  private static void convertDateField(Document doc, String dateField) {
    String statDate = (String) doc.get(dateField);
    doc.append(dateField, Instant.parse(statDate));
  }

  private static boolean isDateField(String fieldName) {
    String name = fieldName.toLowerCase();
    return name.contains("_date") || name.contains("date_");
  }
}

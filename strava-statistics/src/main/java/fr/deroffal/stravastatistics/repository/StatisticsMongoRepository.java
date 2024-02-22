package fr.deroffal.stravastatistics.repository;

import static fr.deroffal.stravastatistics.repository.MongoConfiguration.DETAILED_ACTIVITY_COLLECTION;
import static fr.deroffal.stravastatistics.repository.MongoConfiguration.SUMMARY_ACTIVITY_COLLECTION;
import static org.springframework.data.domain.Sort.Direction.DESC;

import fr.deroffal.stravastatistics.app.ActivityWithSummary;
import fr.deroffal.stravastatistics.app.StatisticsRepository;
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

  public StatisticsMongoRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<Instant> getLastRecordedActivityDate() {
    Query query = new Query();
    query.with(Sort.by(DESC, "start_date")).limit(1);
    Document lastDocument = mongoTemplate.findOne(query, Document.class, SUMMARY_ACTIVITY_COLLECTION);
    return Optional.ofNullable(lastDocument)
//        .map(document -> (String) document.get("start_date"))
//        .map(Instant::parse);
        .map(document -> document.getDate("start_date"))
        .map(Date::toInstant);
  }

  @Override
  public void saveActivityWithSummary(ActivityWithSummary activityWithSummary) {
    saveSummary(activityWithSummary.summaryActivity().getPayload());
    saveActivity(activityWithSummary.detailedActivity().getPayload());
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

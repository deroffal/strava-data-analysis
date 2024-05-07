package fr.deroffal.stravastatistics.repository;

import static fr.deroffal.stravastatistics.repository.MongoConfiguration.SUMMARY_ACTIVITY_JAVA_COLLECTION;

import fr.deroffal.stravastatistics.model.SummaryActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = SUMMARY_ACTIVITY_JAVA_COLLECTION)
public class SummaryActivityDocument extends SummaryActivity {

  @Id
  private ObjectId _id;
}

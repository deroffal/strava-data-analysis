package fr.deroffal.stravastatistics.repository;

import static fr.deroffal.stravastatistics.repository.MongoConfiguration.DETAILED_ACTIVITY_COLLECTION;

import fr.deroffal.stravastatistics.model.DetailedActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = DETAILED_ACTIVITY_COLLECTION)
public class DetailedActivityDocument extends DetailedActivity {

  @Id
  private ObjectId _id;
}

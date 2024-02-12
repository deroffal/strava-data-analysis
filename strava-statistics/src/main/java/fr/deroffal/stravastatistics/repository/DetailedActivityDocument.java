package fr.deroffal.stravastatistics.repository;

import fr.deroffal.stravastatistics.model.DetailedActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "strava_detailed_activity")
public class DetailedActivityDocument extends DetailedActivity {

  @Id
  private ObjectId _id;
}

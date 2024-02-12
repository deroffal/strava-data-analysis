package fr.deroffal.stravastatistics.repository;

import fr.deroffal.stravastatistics.model.SummaryActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "strava_summary_activity")
public class SummaryActivityDocument extends SummaryActivity {

  @Id
  private ObjectId _id;
}

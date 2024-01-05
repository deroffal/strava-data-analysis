package fr.deroffal.stravastatistics.repository;

import fr.deroffal.stravastatistics.model.DetailedActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activity")
public class ActivityDocument extends DetailedActivity {

  @Id
  private ObjectId _id;
}

package fr.deroffal.stravastatistics.repository;

import fr.deroffal.stravastatistics.client.model.SummaryActivity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summary")
public class SummaryDocument extends SummaryActivity {

  @Id
  private ObjectId _id;
}

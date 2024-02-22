package fr.deroffal.stravastatistics.client;

import fr.deroffal.stravastatistics.client.rate.ApiRate;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import java.util.Collection;

public record FetchActivitiesResponse(Collection<CustomSummaryActivity> activities, ApiRate quarterHourRate, ApiRate dailyRate) {

}

package fr.deroffal.stravastatistics.client;

import fr.deroffal.stravastatistics.client.rate.ApiRate;
import fr.deroffal.stravastatistics.model.SummaryActivity;
import java.util.Collection;

public record FetchActivitiesResponse(Collection<SummaryActivity> activities, ApiRate quarterHourRate, ApiRate dailyRate) {

}

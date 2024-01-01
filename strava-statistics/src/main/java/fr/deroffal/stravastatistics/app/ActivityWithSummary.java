package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.client.model.DetailedActivity;
import fr.deroffal.stravastatistics.client.model.SummaryActivity;

public record ActivityWithSummary(SummaryActivity summaryActivity, DetailedActivity detailedActivity){
}

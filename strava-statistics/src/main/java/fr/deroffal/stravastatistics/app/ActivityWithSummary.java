package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;

public record ActivityWithSummary(SummaryActivity summaryActivity, DetailedActivity detailedActivity){
}

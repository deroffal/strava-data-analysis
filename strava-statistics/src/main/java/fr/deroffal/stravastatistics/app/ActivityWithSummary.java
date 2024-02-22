package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.model.CustomDetailedActivity;
import fr.deroffal.stravastatistics.model.CustomSummaryActivity;
import fr.deroffal.stravastatistics.model.DetailedActivity;
import fr.deroffal.stravastatistics.model.SummaryActivity;

public record ActivityWithSummary(CustomSummaryActivity summaryActivity, CustomDetailedActivity detailedActivity){
}

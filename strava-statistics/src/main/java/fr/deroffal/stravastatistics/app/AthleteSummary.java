package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.model.ActivityStats;
import fr.deroffal.stravastatistics.model.DetailedAthlete;
import fr.deroffal.stravastatistics.model.Zones;

public record AthleteSummary(DetailedAthlete detailedAthlete, Zones zones, ActivityStats stats) {
}

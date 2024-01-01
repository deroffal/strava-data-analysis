package fr.deroffal.stravastatistics.app;

import fr.deroffal.stravastatistics.client.model.ActivityStats;
import fr.deroffal.stravastatistics.client.model.DetailedAthlete;
import fr.deroffal.stravastatistics.client.model.Zones;

public record AthleteSummary(DetailedAthlete detailedAthlete, Zones zones, ActivityStats stats) {
}

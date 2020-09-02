package be.aga.dominionSimulator.stats;

import be.aga.dominionSimulator.DomBoard;

import java.util.ArrayList;
import java.util.Collection;

public class StatsManager {

    private static final SingleGameStatsExtractor statsExtractor = new SingleGameStatsExtractor();
    // Since all simulation currently only runs in a single stream it's ok to use ArrayList here.
    private static final Collection<SingleGameStats> stats = new ArrayList<>();
    public static boolean gatherAdditionalStats = false;

    public static SeriesStats calculateCurrentSeriesStats() {
        return gatherAdditionalStats
            ? new AverageStatsCalculator().calculateStats(stats)
            : SeriesStats.EMPTY_SERIES_STATS;
    }

    public static void addStatsToCurrentSeries(DomBoard board) {
        if (gatherAdditionalStats) {
            stats.add(statsExtractor.extractStats(board));
        }
    }

    public static void resetCurrentSeries() {
        stats.clear();
    }
}

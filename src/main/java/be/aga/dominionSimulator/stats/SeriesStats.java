package be.aga.dominionSimulator.stats;

import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SeriesStats {

    public final static SeriesStats EMPTY_SERIES_STATS = new SeriesStats(
        Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap());

    private final List<AverageStatsForPlayer> averageStatsForPlayers;
    private final Map<DomCardName, Double> averagePiles;
    private final Map<DomCardName, Double> averageTrashPile;

    public SeriesStats(List<AverageStatsForPlayer> averageStatsForPlayers,
                       Map<DomCardName, Double> averagePiles,
                       Map<DomCardName, Double> averageTrashPile)
    {
        this.averageStatsForPlayers = averageStatsForPlayers;
        this.averagePiles = averagePiles;
        this.averageTrashPile = averageTrashPile;
    }

    public List<AverageStatsForPlayer> getAverageStatsForPlayers() {
        return averageStatsForPlayers;
    }

    public Map<DomCardName, Double> getAveragePiles() {
        return averagePiles;
    }

    public Map<DomCardName, Double> getAverageTrashPile() {
        return averageTrashPile;
    }

    public static class AverageStatsForPlayer {

        private final String name;
        private final double averageVictoryPoints;
        private final Map<DomCardName, Double> averageCardsInTheDeck;

        public AverageStatsForPlayer(String name, double averageVictoryPoints, Map<DomCardName, Double> averageCardsInTheDeck) {
            this.name = name;
            this.averageVictoryPoints = averageVictoryPoints;
            this.averageCardsInTheDeck = averageCardsInTheDeck;
        }

        public String getName() {
            return name;
        }

        public double getAverageVictoryPoints() {
            return averageVictoryPoints;
        }

        public Map<DomCardName, Double> getAverageCardsInTheDeck() {
            return averageCardsInTheDeck;
        }
    }
}

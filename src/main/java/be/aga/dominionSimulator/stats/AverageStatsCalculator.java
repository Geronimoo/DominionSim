package be.aga.dominionSimulator.stats;

import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static be.aga.dominionSimulator.stats.SingleGameStats.DUMMY_STATS;
import static be.aga.dominionSimulator.stats.SingleGameStats.Player.DUMMY;

public class AverageStatsCalculator {

    public SeriesStats calculateStats(Collection<SingleGameStats> gameByGameStats) {
        Map<DomCardName, Double> averagePiles = calculateAveragePiles(gameByGameStats);
        Set<DomCardName> cardsPresentInEveryGame = averagePiles.keySet();

        return new SeriesStats(
            calculateAverageStatsForPlayers(gameByGameStats, cardsPresentInEveryGame),
            averagePiles,
            calculateAverageTrashPile(gameByGameStats, cardsPresentInEveryGame)
        );
    }

    private Map<DomCardName, Double> calculateAveragePiles(Collection<SingleGameStats> gameByGameStats) {
        return gameByGameStats.stream()
            .map(SingleGameStats::getPiles)
            .flatMap(piles -> piles.entrySet().stream())
            .filter(card -> card.getKey().hasCardType(DomCardType.Kingdom) || card.getKey().hasCardType(DomCardType.Base))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingInt(Map.Entry::getValue)));
    }

    private List<SeriesStats.AverageStatsForPlayer> calculateAverageStatsForPlayers(
        Collection<SingleGameStats> gameByGameStats, Set<DomCardName> cardsPresentInEveryGame)
    {
        int numberOfPlayers = gameByGameStats.stream().findFirst().orElse(DUMMY_STATS).getPlayers().size();
        return IntStream.range(0, numberOfPlayers)
            .mapToObj(i -> extractStatsForSinglePlayer(i, gameByGameStats))
            .map(player -> calculateAverageStatsForPlayer(player, cardsPresentInEveryGame))
            .collect(Collectors.toList());
    }

    private List<SingleGameStats.Player> extractStatsForSinglePlayer(int playerIndex, Collection<SingleGameStats> gameByGameStats) {
        return gameByGameStats.stream()
            .map(stats -> stats.getPlayers().get(playerIndex))
            .collect(Collectors.toList());
    }

    private SeriesStats.AverageStatsForPlayer calculateAverageStatsForPlayer(
        List<SingleGameStats.Player> players, Set<DomCardName> cardsPresentInEveryGame)
    {
        Map<DomCardName, Double> averageCardsInPlayerDeck = players.stream()
            .map(SingleGameStats.Player::getCardsInDeck)
            .flatMap(cards -> cards.entrySet().stream())
            .filter(card -> cardsPresentInEveryGame.contains(card.getKey()))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingInt(Map.Entry::getValue)));

        double averageVictoryPoints = players.stream()
            .map(SingleGameStats.Player::getVictoryPoints)
            .collect(Collectors.averagingInt(Integer::intValue));

        return new SeriesStats.AverageStatsForPlayer(
            players.stream().findFirst().orElse(DUMMY).getName(),
            averageVictoryPoints, averageCardsInPlayerDeck);
    }


    private Map<DomCardName, Double> calculateAverageTrashPile(
        Collection<SingleGameStats> singleGameStats, Set<DomCardName> cardsPresentInEveryGame)
    {
        return singleGameStats.stream()
            .map(SingleGameStats::getTrashPile)
            .flatMap(cards -> cards.entrySet().stream())
            .filter(card -> cardsPresentInEveryGame.contains(card.getKey()))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingInt(Map.Entry::getValue)));
    }
}

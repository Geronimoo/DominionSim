package be.aga.dominionSimulator.stats;

import be.aga.dominionSimulator.DomBoard;
import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SingleGameStatsExtractor {

    public SingleGameStats extractStats(DomBoard board) {
        List<SingleGameStats.Player> players = board
            .getPlayers().stream()
            .map(this::extractPlayerInfo)
            .collect(Collectors.toList());
        Map<DomCardName, Integer>  piles = board
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, pile -> pile.getValue().size()));
        Map<DomCardName, Integer>  trashPile = board
            .getTrashedCards().stream()
            .collect(Collectors.groupingBy(DomCard::getName, Collectors.reducing(0, e -> 1, Integer::sum)));

        return new SingleGameStats(players, piles, trashPile);
    }

    private SingleGameStats.Player extractPlayerInfo(DomPlayer player) {
        Map<DomCardName, Integer> cards = player
            .getDeck().entrySet().stream()
            .filter(pile -> !pile.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, pile -> pile.getValue().size()));
        return new SingleGameStats.Player(player.getName(), player.countVictoryPoints(), cards);
    }
}

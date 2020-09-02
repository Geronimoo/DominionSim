package be.aga.dominionSimulator.stats;

import be.aga.dominionSimulator.enums.DomCardName;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SingleGameStats {

    public static final SingleGameStats DUMMY_STATS =
        new SingleGameStats(Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap());

    private final List<Player> players;
    private final Map<DomCardName, Integer> piles;
    private final Map<DomCardName, Integer> trashPile;

    public SingleGameStats(List<Player> players, Map<DomCardName, Integer> piles, Map<DomCardName, Integer> trashPile) {
        this.players = players;
        this.piles = piles;
        this.trashPile = trashPile;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<DomCardName, Integer> getPiles() {
        return piles;
    }

    public Map<DomCardName, Integer> getTrashPile() {
        return trashPile;
    }

    static class Player {
        public static Player DUMMY = new Player("JOHN DOE", 0, Collections.emptyMap());

        private final String name;
        private final int victoryPoints;
        private final Map<DomCardName, Integer> cardsInDeck;

        public Player(String name, int victoryPoints, Map<DomCardName, Integer> cardsInDeck) {
            this.name = name;
            this.victoryPoints = victoryPoints;
            this.cardsInDeck = cardsInDeck;
        }

        public String getName() {
            return name;
        }

        public int getVictoryPoints() {
            return victoryPoints;
        }

        public Map<DomCardName, Integer> getCardsInDeck() {
            return cardsInDeck;
        }
    }
}

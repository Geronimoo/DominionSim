package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.HashSet;
import java.util.Set;

public class GatekeeperCard extends DomCard {
    private Set<DomPlayer> protectedOpponents = new HashSet<DomPlayer>();

    public GatekeeperCard() {
      super( DomCardName.Gatekeeper);
    }

    @Override
    public void play() {
        for (DomPlayer theOpp : owner.getOpponents())
            if (theOpp.checkDefense())
                protectedOpponents.add(theOpp);
    }

    public void resolveDuration() {
      protectedOpponents.clear();
      owner.addAvailableCoins(3);
    }

    public boolean hasProtectedOpponent(DomPlayer domPlayer) {
        return protectedOpponents.contains(domPlayer);
    }

    @Override
    public void cleanVariablesFromPreviousGames() {
        protectedOpponents.clear();
    }
}
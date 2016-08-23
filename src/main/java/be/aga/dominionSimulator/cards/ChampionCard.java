package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class ChampionCard extends DomCard {
    public ChampionCard() {
      super( DomCardName.Champion);
    }

    @Override
    public void handleCleanUpPhase() {
        owner.addToCardsToStayInPlay(this);
    }

    @Override
    public boolean mustStayInPlay() {
        return true;
    }

    @Override
    public boolean wantsToBeMultiplied() {
        return false;
    }
}
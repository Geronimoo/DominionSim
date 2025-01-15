package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class SamuraiCard extends DomCard {
    public SamuraiCard() {
      super( DomCardName.Samurai);
    }

    @Override
    public void play() {
        for (DomPlayer thePlayer : owner.getOpponents()) {
            if (!thePlayer.checkDefense()) {
                thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
            }
        }
    }

    public void resolveDuration() {
      owner.addAvailableCoins(1);
    }

    @Override
    public boolean mustStayInPlay() {
        return true;
    }
}
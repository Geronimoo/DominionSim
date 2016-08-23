package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomCost;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

import java.util.ArrayList;

public class GiantCard extends DomCard {
    public GiantCard() {
      super( DomCardName.Giant);
    }

    public void play() {
      owner.flipJourneyToken();
      if (!owner.isJourneyTokenFaceUp()) {
          owner.addAvailableCoins(1);
          return;
      }
      owner.addAvailableCoins(5);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (thePlayer.checkDefense() )
        	continue;
        ArrayList< DomCard > theCards = thePlayer.revealTopCards(1);
        if (theCards.isEmpty())
            continue;
        DomCard theCard = theCards.get(0);
        if (theCard.getCoinCost(owner.getCurrentGame())>=3 && theCard.getCoinCost(owner.getCurrentGame())<=6 && theCard.getPotionCost()==0) {
          thePlayer.trash(theCard);
        } else {
          thePlayer.discard(theCard);
          thePlayer.gain(DomCardName.Curse);
        }
      }
    }
}
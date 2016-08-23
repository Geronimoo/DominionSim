package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class GoonsCard extends DomCard {
    public GoonsCard () {
      super( DomCardName.Goons);
    }

    public void play() {
      owner.addAvailableCoins(2);
      owner.addAvailableBuys( 1 );
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
        }
      }
    }
}
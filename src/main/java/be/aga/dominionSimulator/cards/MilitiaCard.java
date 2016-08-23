package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class MilitiaCard extends DomCard {
    public MilitiaCard () {
      super( DomCardName.Militia);
    }

    public void play() {
      owner.addAvailableCoins(2);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
        }
      }
    }
}
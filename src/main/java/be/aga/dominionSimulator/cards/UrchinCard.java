package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class UrchinCard extends DomCard {
    public UrchinCard() {
      super( DomCardName.Urchin);
    }

    public void play() {
      owner.addActions(1);
      owner.drawCards(1);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-4, false);
        }
      }
    }
}
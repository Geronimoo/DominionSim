package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;
import be.aga.dominionSimulator.enums.DomCardType;

public class NinjaCard extends DomCard {
    public NinjaCard() {
      super( DomCardName.Ninja);
    }

    public void play() {
      owner.drawCards(1);
      for (DomPlayer thePlayer : owner.getOpponents()) {
        if (!thePlayer.checkDefense()) {
          thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size()-3, false);
        }
      }
    }
}
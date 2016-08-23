package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;
import be.aga.dominionSimulator.enums.DomCardName;

public class LegionaryCard extends DomCard {
    public LegionaryCard() {
      super( DomCardName.Legionary);
    }

    public void play() {
      owner.addAvailableCoins(3);
      if (owner.getCardsFromHand(DomCardName.Gold).isEmpty())
        return;
      if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals " + owner.getCardsFromHand(DomCardName.Gold).get(0));
      for (DomPlayer thePlayer : owner.getOpponents()) {
          if (!thePlayer.checkDefense()) {
              thePlayer.doForcedDiscard(thePlayer.getCardsInHand().size() - 2, false);
              thePlayer.drawCards(1);
          }
      }
    }
}
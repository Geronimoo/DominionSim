package be.aga.dominionSimulator.cards;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.enums.DomCardName;

public class EncampmentCard extends DomCard {
    public EncampmentCard() {
      super( DomCardName.Encampment);
    }

    public void play() {
      owner.addActions(2);
      owner.drawCards(2);
      if (!owner.getCardsFromHand(DomCardName.Gold).isEmpty() || !owner.getCardsFromHand(DomCardName.Plunder).isEmpty()) {
          if (DomEngine.haveToLog) DomEngine.addToLog( owner + " reveals Gold or Plunder");
          return;
      }
      if (owner.getCardsFromPlay(getName()).contains(this))
          owner.setAside(owner.removeCardFromPlay(this));
    }
}